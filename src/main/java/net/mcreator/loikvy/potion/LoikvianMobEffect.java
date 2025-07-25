package net.mcreator.loikvy.potion;

import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.loikvy.init.LoikvyModParticleTypes;
import net.mcreator.loikvy.init.LoikvyModMobEffects;
import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvianMobEffect extends MobEffect {
	public LoikvianMobEffect() {
		super(MobEffectCategory.NEUTRAL, -1);
		this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.loikvian_0"), -0.925, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.loikvian_1"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MINING_EFFICIENCY, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.loikvian_2"), 4.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "effect.loikvian_3"), -0.85, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
		return (SimpleParticleType) (LoikvyModParticleTypes.NO_PART.get());
	}

	@SubscribeEvent
	public static void registerMobEffectExtensions(RegisterClientExtensionsEvent event) {
		event.registerMobEffect(new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		}, LoikvyModMobEffects.LOIKVIAN.get());
	}
}