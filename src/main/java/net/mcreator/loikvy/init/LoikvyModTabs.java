/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LoikvyMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(LoikvyModItems.CALENDAR.get());
			tabData.accept(LoikvyModItems.BASIC_CLOCK.get());
			tabData.accept(LoikvyModItems.GASOLINE_BUCKET.get());
			tabData.accept(LoikvyModItems.KITCHEN_KNIFE.get());
			tabData.accept(LoikvyModBlocks.COOKING_PAN.get().asItem());
			tabData.accept(LoikvyModItems.BAG.get());
			tabData.accept(LoikvyModItems.LOCK_REMOVER.get());
			tabData.accept(LoikvyModItems.WEIGHT_SMALL_ONE_HANDED.get());
			tabData.accept(LoikvyModItems.STONE_AXE.get());
			tabData.accept(LoikvyModItems.CHISEL.get());
			tabData.accept(LoikvyModItems.SMALL_ROCK.get());
			tabData.accept(LoikvyModItems.SHARP_ROCK.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			tabData.accept(LoikvyModBlocks.FRIDGE.get().asItem());
			tabData.accept(LoikvyModBlocks.FREEZER.get().asItem());
			tabData.accept(LoikvyModBlocks.SMOKE_DETECTOR.get().asItem());
			tabData.accept(LoikvyModBlocks.GAMBLING_BLACK_JACK_TABLE.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			tabData.accept(LoikvyModItems.BURGER.get());
			tabData.accept(LoikvyModBlocks.CUTTING_BOARD.get().asItem());
			tabData.accept(LoikvyModItems.TOMATO.get());
			tabData.accept(LoikvyModItems.TOMATO_SLICE.get());
			tabData.accept(LoikvyModItems.LETTUCE.get());
			tabData.accept(LoikvyModItems.LETTUCE_LEAF.get());
			tabData.accept(LoikvyModItems.CHEESE_BURGER.get());
			tabData.accept(LoikvyModItems.PEPPERONI_PIZZA.get());
			tabData.accept(LoikvyModItems.PIZZA_SLICE.get());
			tabData.accept(LoikvyModItems.FLOUR.get());
			tabData.accept(LoikvyModItems.TABLE_SPOON.get());
			tabData.accept(LoikvyModItems.TEA_SPOON.get());
			tabData.accept(LoikvyModItems.SUNFLOWER_OIL.get());
			tabData.accept(LoikvyModItems.BURNT_FOOD.get());
			tabData.accept(LoikvyModItems.UNBAKED_PEPPERONI_PIZZA.get());
			tabData.accept(LoikvyModItems.DOUGH.get());
			tabData.accept(LoikvyModItems.CHEESE_PIZZA.get());
			tabData.accept(LoikvyModItems.CHEESE_PIZZA_SLICE.get());
			tabData.accept(LoikvyModItems.UNBAKED_CHEESE_PIZZA.get());
			tabData.accept(LoikvyModItems.PEPPERONI_CHUNK.get());
			tabData.accept(LoikvyModItems.PEPPERONI_SLICE.get());
			tabData.accept(LoikvyModItems.ENERGY_DRINK.get());
			tabData.accept(LoikvyModItems.EMPTY_CAN.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			tabData.accept(LoikvyModItems.AA_BATTERY.get());
			tabData.accept(LoikvyModBlocks.COAL_GENERATOR.get().asItem());
			tabData.accept(LoikvyModItems.PHONE.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			tabData.accept(LoikvyModItems.WHEAT_FLOUR.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			tabData.accept(LoikvyModItems.LETTUCE_SEED.get());
			tabData.accept(LoikvyModItems.RIPPED_SHEET.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(LoikvyModItems.SHOP_CASHIER_SPAWN_EGG.get());
			tabData.accept(LoikvyModItems.WHEELCHAIR_SPAWN_EGG.get());
			tabData.accept(LoikvyModItems.GROUND_ITEM_SPAWN_EGG.get());
		}
	}
}