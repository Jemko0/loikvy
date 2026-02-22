package net.mcreator.loikvy.mixin.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightTexture.class)
public class LightTextureMixin {

    @Shadow private DynamicTexture lightTexture;

    private static final float[][] LUMINANCE = new float[16][16];

    private static float luminance(float r, float g, float b) {
        return r * 0.2126f + g * 0.7152f + b * 0.0722f;
    }

    private static int darken(int c, int blockIndex, int skyIndex) {
        final float lTarget = LUMINANCE[blockIndex][skyIndex];
        final float r = (c & 0xFF) / 255f;
        final float g = ((c >> 8) & 0xFF) / 255f;
        final float b = ((c >> 16) & 0xFF) / 255f;
        final float l = luminance(r, g, b);
        final float f = l > 0 ? Math.min(1, lTarget / l) : 0;

        return f == 1f ? c
                : 0xFF000000
                | Math.round(f * r * 255)
                | (Math.round(f * g * 255) << 8)
                | (Math.round(f * b * 255) << 16);
    }

    private static float skyFactor(ClientLevel world) {
        if (world.dimensionType().hasSkyLight()) {
            final float angle = world.getTimeOfDay(0);
            if (angle > 0.25f && angle < 0.75f) {
                // nighttime no moon phase, just return 0 at night
                final float oldWeight = Math.max(0, (Math.abs(angle - 0.5f) - 0.2f)) * 20;
                return Mth.lerp(oldWeight * oldWeight * oldWeight, 0.0f, 1.0f);
            } else {
                return 1;
            }
        } else {
            // no sky light (nether etc) fully dark
            return 0;
        }
    }

    private static void updateLuminance(float tickDelta, Minecraft client, float prevFlicker) {
        final ClientLevel world = client.level;
        if (world == null) return;

        final GameRenderer worldRenderer = client.gameRenderer;
        final float dimSkyFactor = skyFactor(world);
        final float ambient = world.getSkyDarken(1.0F);
        final DimensionType dim = world.dimensionType();

        for (int skyIndex = 0; skyIndex < 16; ++skyIndex) {
            float skyFactor = 1f - skyIndex / 15f;
            skyFactor = 1 - skyFactor * skyFactor * skyFactor * skyFactor;
            skyFactor *= dimSkyFactor;

            float min = skyFactor * 0.05f;
            final float rawAmbient = ambient * skyFactor;
            final float minAmbient = rawAmbient * (1 - min) + min;
            final float skyBase = LightTexture.getBrightness(dim, skyIndex) * minAmbient;

            min = 0.35f * skyFactor;
            float skyRed   = skyBase * (rawAmbient * (1 - min) + min);
            float skyGreen = skyBase * (rawAmbient * (1 - min) + min);
            float skyBlue  = skyBase;

            if (worldRenderer.getDarkenWorldAmount(tickDelta) > 0.0F) {
                final float skyDarkness = worldRenderer.getDarkenWorldAmount(tickDelta);
                skyRed   = skyRed   * (1.0F - skyDarkness) + skyRed   * 0.7F * skyDarkness;
                skyGreen = skyGreen * (1.0F - skyDarkness) + skyGreen * 0.6F * skyDarkness;
                skyBlue  = skyBlue  * (1.0F - skyDarkness) + skyBlue  * 0.6F * skyDarkness;
            }

            for (int blockIndex = 0; blockIndex < 16; ++blockIndex) {
                float blockFactor = 1f - blockIndex / 15f;
                blockFactor = 1 - blockFactor * blockFactor * blockFactor * blockFactor;

                final float blockBase = blockFactor * LightTexture.getBrightness(dim, blockIndex)
                        * (prevFlicker * 0.1F + 1.5F);
                min = 0.4f * blockFactor;
                final float blockGreen = blockBase * ((blockBase * (1 - min) + min) * (1 - min) + min);
                final float blockBlue  = blockBase * (blockBase * blockBase * (1 - min) + min);

                float red   = skyRed   + blockBase;
                float green = skyGreen + blockGreen;
                float blue  = skyBlue  + blockBlue;

                final float f = Math.max(skyFactor, blockFactor);
                min = 0.03f * f;
                red   = red   * (0.99F - min) + min;
                green = green * (0.99F - min) + min;
                blue  = blue  * (0.99F - min) + min;

                // gamma (brightness slider)
                final float gamma = client.options.gamma().get().floatValue();
                float invRed   = 1.0F - red;
                float invGreen = 1.0F - green;
                float invBlue  = 1.0F - blue;
                invRed   = 1.0F - invRed   * invRed   * invRed   * invRed;
                invGreen = 1.0F - invGreen * invGreen * invGreen * invGreen;
                invBlue  = 1.0F - invBlue  * invBlue  * invBlue  * invBlue;
                red   = red   * (1.0F - gamma) + invRed   * gamma;
                green = green * (1.0F - gamma) + invGreen * gamma;
                blue  = blue  * (1.0F - gamma) + invBlue  * gamma;

                min = 0.03f * f;
                red   = red   * (0.99F - min) + min;
                green = green * (0.99F - min) + min;
                blue  = blue  * (0.99F - min) + min;

                red   = Mth.clamp(red,   0f, 1f);
                green = Mth.clamp(green, 0f, 1f);
                blue  = Mth.clamp(blue,  0f, 1f);

                LUMINANCE[blockIndex][skyIndex] = Math.min(1.0f, luminance(red, green, blue) * 1.5f);
            }
        }
    }

    boolean enabled = true;

    @Inject(method = "updateLightTexture", at = @At("RETURN"))
    private void onLightmapUpdated(float partialTick, CallbackInfo ci) {
        if(!enabled) return;

        Minecraft client = Minecraft.getInstance();
        NativeImage pixels = this.lightTexture.getPixels();
        if (pixels == null || client.level == null) return;

        updateLuminance(partialTick, client, 0.0f);

        for (int sky = 0; sky < 16; sky++) {
            for (int block = 0; block < 16; block++) {
                int color = pixels.getPixelRGBA(block, sky);

                // FIX: If we are at the very edge of the lightmap (often used for UI/Fullbright)
                // or if the indices are at their maximum, let the original color pass through.
                if (block == 15 && sky == 15) {
                    pixels.setPixelRGBA(block, sky, color);
                } else {
                    pixels.setPixelRGBA(block, sky, darken(color, block, sky));
                }
            }
        }

        this.lightTexture.upload();
    }
}