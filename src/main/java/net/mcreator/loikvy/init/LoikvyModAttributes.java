/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, LoikvyMod.MODID);
	public static final DeferredHolder<Attribute, Attribute> ENCUMBRANCE = REGISTRY.register("encumbrance", () -> new RangedAttribute("attribute.loikvy.encumbrance", 0, 0, 50).setSyncable(true));

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, ENCUMBRANCE);
	}
}