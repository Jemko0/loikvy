package net.mcreator.loikvy.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.mcreator.loikvy.entity.PlayerCorpseEntity;

import com.mojang.authlib.GameProfile;

public class PlayerCorpseRenderer extends HumanoidMobRenderer<PlayerCorpseEntity, HumanoidModel<PlayerCorpseEntity>> {
    public PlayerCorpseRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<PlayerCorpseEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(PlayerCorpseEntity entity)
    {
        GameProfile profile = entity.getPlayerProfile();

        ResourceLocation skinResource = ResourceLocation.parse("loikvy:textures/entities/2025_07_07_mango-fruit-skin-23384942.png");
        
        if (profile != null) {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerSkin playerSkin = minecraft.getSkinManager().getInsecureSkin(profile);
            
            if (playerSkin != null) {
                skinResource = playerSkin.texture();
            } else {
            	skinResource = DefaultPlayerSkin.getDefaultTexture();
            }
        }
        
        return skinResource;
    }

    @Override
    protected void setupRotations(PlayerCorpseEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
    
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        poseStack.translate(0.0, -0.4, 0.0);
    }
}