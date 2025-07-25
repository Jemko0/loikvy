package net.mcreator.loikvy.potion;

import net.neoforged.neoforge.common.NeoForgeMod;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.loikvy.procedures.DrunkOnEffectActiveTickProcedure;
import net.mcreator.loikvy.LoikvyMod;

public class DrunkMobEffect extends MobEffect {
	public DrunkMobEffect() {
		super(MobEffectCategory.HARMFUL, -1474432);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_0"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_1"), -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_2"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_3"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_4"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_5"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.OXYGEN_BONUS, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_6"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.drunk_7"), -0.15, AttributeModifier.Operation.ADD_VALUE);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return ParticleTypes.BUBBLE_POP;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		DrunkOnEffectActiveTickProcedure.execute(entity);
		return super.applyEffectTick(entity, amplifier);
	}
}