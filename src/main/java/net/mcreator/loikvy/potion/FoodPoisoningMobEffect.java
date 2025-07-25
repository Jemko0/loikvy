package net.mcreator.loikvy.potion;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.loikvy.procedures.FoodPoisoningOnEffectActiveTickProcedure;
import net.mcreator.loikvy.init.LoikvyModParticleTypes;
import net.mcreator.loikvy.LoikvyMod;

public class FoodPoisoningMobEffect extends MobEffect {
	public FoodPoisoningMobEffect() {
		super(MobEffectCategory.HARMFUL, -16777216);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.food_poisoning_0"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.food_poisoning_1"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.food_poisoning_2"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.food_poisoning_3"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.food_poisoning_4"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return (SimpleParticleType) (LoikvyModParticleTypes.NO_PART.get());
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		FoodPoisoningOnEffectActiveTickProcedure.execute(entity.level(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}