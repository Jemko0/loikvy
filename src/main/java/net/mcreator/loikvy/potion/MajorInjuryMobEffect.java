package net.mcreator.loikvy.potion;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.loikvy.init.LoikvyModParticleTypes;
import net.mcreator.loikvy.LoikvyMod;

public class MajorInjuryMobEffect extends MobEffect {
	public MajorInjuryMobEffect() {
		super(MobEffectCategory.NEUTRAL, -1);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.major_injury_0"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return (SimpleParticleType) (LoikvyModParticleTypes.NO_PART.get());
	}
}