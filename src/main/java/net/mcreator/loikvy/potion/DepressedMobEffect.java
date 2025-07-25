package net.mcreator.loikvy.potion;

import net.neoforged.neoforge.common.NeoForgeMod;

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

public class DepressedMobEffect extends MobEffect {
	public DepressedMobEffect() {
		super(MobEffectCategory.NEUTRAL, -16764007);
		this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_0"), -0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_1"), -0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_2"), -0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_3"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_4"), -0.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_5"), -0.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_6"), 0.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_7"), -0.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_8"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_9"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.OXYGEN_BONUS, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_10"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_11"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.depressed_12"), -0.33, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return (SimpleParticleType) (LoikvyModParticleTypes.NO_PART.get());
	}
}