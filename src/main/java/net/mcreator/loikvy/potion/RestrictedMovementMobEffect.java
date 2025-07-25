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

public class RestrictedMovementMobEffect extends MobEffect {
	public RestrictedMovementMobEffect() {
		super(MobEffectCategory.NEUTRAL, -65536);
		this.addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.restricted_movement_0"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return ParticleTypes.CRIT;
	}
}