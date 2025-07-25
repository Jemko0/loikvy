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

public class ParalyzedMobEffect extends MobEffect {
	public ParalyzedMobEffect() {
		super(MobEffectCategory.NEUTRAL, -1);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.paralyzed_0"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.paralyzed_1"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.paralyzed_2"), 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.paralyzed_3"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.paralyzed_4"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MOVEMENT_EFFICIENCY, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.paralyzed_5"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.paralyzed_6"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return (SimpleParticleType) (LoikvyModParticleTypes.NO_PART.get());
	}
}