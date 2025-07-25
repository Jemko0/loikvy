package net.mcreator.loikvy.potion;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.loikvy.LoikvyMod;

public class StinkingMobEffect extends MobEffect {
	public StinkingMobEffect() {
		super(MobEffectCategory.HARMFUL, -16751104);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.stinking_0"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return ParticleTypes.GLOW_SQUID_INK;
	}
}