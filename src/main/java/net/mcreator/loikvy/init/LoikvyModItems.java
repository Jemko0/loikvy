/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;

import net.mcreator.loikvy.procedures.RippedSheetPropertyValueProviderProcedure;
import net.mcreator.loikvy.procedures.PhonePropertyValueProviderProcedure;
import net.mcreator.loikvy.procedures.BasicClockPropertyValueProviderProcedure;
import net.mcreator.loikvy.item.inventory.PhoneInventoryCapability;
import net.mcreator.loikvy.item.WheatFlourItem;
import net.mcreator.loikvy.item.WeightSmallOneHandedItem;
import net.mcreator.loikvy.item.UnbakedPizzaItem;
import net.mcreator.loikvy.item.UnbakedCheesePizzaItem;
import net.mcreator.loikvy.item.TomatoSliceItem;
import net.mcreator.loikvy.item.TomatoItem;
import net.mcreator.loikvy.item.TeaspoonItem;
import net.mcreator.loikvy.item.TableSpoonItem;
import net.mcreator.loikvy.item.SunflowerOilItem;
import net.mcreator.loikvy.item.RottenFoodItem;
import net.mcreator.loikvy.item.RippedSheetItem;
import net.mcreator.loikvy.item.PizzaSliceItem;
import net.mcreator.loikvy.item.PizzaItem;
import net.mcreator.loikvy.item.PhoneItem;
import net.mcreator.loikvy.item.PepperoniSliceItem;
import net.mcreator.loikvy.item.PepperoniChunkItem;
import net.mcreator.loikvy.item.LockRemoverItem;
import net.mcreator.loikvy.item.LettuceSeedItem;
import net.mcreator.loikvy.item.LettuceLeafItem;
import net.mcreator.loikvy.item.LettuceItem;
import net.mcreator.loikvy.item.KitchenKnifeItem;
import net.mcreator.loikvy.item.GasolineItem;
import net.mcreator.loikvy.item.FlourItem;
import net.mcreator.loikvy.item.DoughItem;
import net.mcreator.loikvy.item.DoorLockerItem;
import net.mcreator.loikvy.item.DoorKeyItem;
import net.mcreator.loikvy.item.DebugViewStatsItem;
import net.mcreator.loikvy.item.CheeseWheelItem;
import net.mcreator.loikvy.item.CheeseSliceItem;
import net.mcreator.loikvy.item.CheesePizzaSliceItem;
import net.mcreator.loikvy.item.CheesePizzaItem;
import net.mcreator.loikvy.item.CheeseBurgerItem;
import net.mcreator.loikvy.item.CalendarItem;
import net.mcreator.loikvy.item.BurntFoodItem;
import net.mcreator.loikvy.item.BurgerItem;
import net.mcreator.loikvy.item.BasicClockItem;
import net.mcreator.loikvy.item.BagItem;
import net.mcreator.loikvy.item.AntidepressantsItem;
import net.mcreator.loikvy.item.AABatteryItem;
import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(LoikvyMod.MODID);
	public static final DeferredItem<Item> CALENDAR = REGISTRY.register("calendar", CalendarItem::new);
	public static final DeferredItem<Item> BASIC_CLOCK = REGISTRY.register("basic_clock", BasicClockItem::new);
	public static final DeferredItem<Item> DEBUG_VIEW_STATS = REGISTRY.register("debug_view_stats", DebugViewStatsItem::new);
	public static final DeferredItem<Item> GASOLINE_BUCKET = REGISTRY.register("gasoline_bucket", GasolineItem::new);
	public static final DeferredItem<Item> ROTTEN_FOOD = REGISTRY.register("rotten_food", RottenFoodItem::new);
	public static final DeferredItem<Item> DOOR_LOCKER = REGISTRY.register("door_locker", DoorLockerItem::new);
	public static final DeferredItem<Item> DOOR_KEY = REGISTRY.register("door_key", DoorKeyItem::new);
	public static final DeferredItem<Item> FRIDGE = block(LoikvyModBlocks.FRIDGE);
	public static final DeferredItem<Item> FREEZER = block(LoikvyModBlocks.FREEZER);
	public static final DeferredItem<Item> BURGER = REGISTRY.register("burger", BurgerItem::new);
	public static final DeferredItem<Item> CUTTING_BOARD = block(LoikvyModBlocks.CUTTING_BOARD, new Item.Properties().stacksTo(1));
	public static final DeferredItem<Item> CHEESE_WHEEL = REGISTRY.register("cheese_wheel", CheeseWheelItem::new);
	public static final DeferredItem<Item> CHEESE_SLICE = REGISTRY.register("cheese_slice", CheeseSliceItem::new);
	public static final DeferredItem<Item> KITCHEN_KNIFE = REGISTRY.register("kitchen_knife", KitchenKnifeItem::new);
	public static final DeferredItem<Item> TOMATO = REGISTRY.register("tomato", TomatoItem::new);
	public static final DeferredItem<Item> TOMATO_SLICE = REGISTRY.register("tomato_slice", TomatoSliceItem::new);
	public static final DeferredItem<Item> LETTUCE = REGISTRY.register("lettuce", LettuceItem::new);
	public static final DeferredItem<Item> LETTUCE_LEAF = REGISTRY.register("lettuce_leaf", LettuceLeafItem::new);
	public static final DeferredItem<Item> CHEESE_BURGER = REGISTRY.register("cheese_burger", CheeseBurgerItem::new);
	public static final DeferredItem<Item> PEPPERONI_PIZZA = REGISTRY.register("pepperoni_pizza", PizzaItem::new);
	public static final DeferredItem<Item> PIZZA_SLICE = REGISTRY.register("pizza_slice", PizzaSliceItem::new);
	public static final DeferredItem<Item> FLOUR = REGISTRY.register("flour", FlourItem::new);
	public static final DeferredItem<Item> TABLE_SPOON = REGISTRY.register("table_spoon", TableSpoonItem::new);
	public static final DeferredItem<Item> TEA_SPOON = REGISTRY.register("tea_spoon", TeaspoonItem::new);
	public static final DeferredItem<Item> COOKING_PAN = block(LoikvyModBlocks.COOKING_PAN, new Item.Properties().stacksTo(1));
	public static final DeferredItem<Item> SUNFLOWER_OIL = REGISTRY.register("sunflower_oil", SunflowerOilItem::new);
	public static final DeferredItem<Item> BURNT_FOOD = REGISTRY.register("burnt_food", BurntFoodItem::new);
	public static final DeferredItem<Item> SMOKE_DETECTOR = block(LoikvyModBlocks.SMOKE_DETECTOR);
	public static final DeferredItem<Item> AA_BATTERY = REGISTRY.register("aa_battery", AABatteryItem::new);
	public static final DeferredItem<Item> UNBAKED_PEPPERONI_PIZZA = REGISTRY.register("unbaked_pepperoni_pizza", UnbakedPizzaItem::new);
	public static final DeferredItem<Item> DOUGH = REGISTRY.register("dough", DoughItem::new);
	public static final DeferredItem<Item> BAG = REGISTRY.register("bag", BagItem::new);
	public static final DeferredItem<Item> WHEAT_FLOUR = REGISTRY.register("wheat_flour", WheatFlourItem::new);
	public static final DeferredItem<Item> CHEESE_PIZZA = REGISTRY.register("cheese_pizza", CheesePizzaItem::new);
	public static final DeferredItem<Item> CHEESE_PIZZA_SLICE = REGISTRY.register("cheese_pizza_slice", CheesePizzaSliceItem::new);
	public static final DeferredItem<Item> UNBAKED_CHEESE_PIZZA = REGISTRY.register("unbaked_cheese_pizza", UnbakedCheesePizzaItem::new);
	public static final DeferredItem<Item> PEPPERONI_CHUNK = REGISTRY.register("pepperoni_chunk", PepperoniChunkItem::new);
	public static final DeferredItem<Item> PEPPERONI_SLICE = REGISTRY.register("pepperoni_slice", PepperoniSliceItem::new);
	public static final DeferredItem<Item> COAL_GENERATOR = block(LoikvyModBlocks.COAL_GENERATOR, new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
	public static final DeferredItem<Item> PHONE = REGISTRY.register("phone", PhoneItem::new);
	public static final DeferredItem<Item> STOVE = block(LoikvyModBlocks.STOVE);
	public static final DeferredItem<Item> LETTUCE_CROP = block(LoikvyModBlocks.LETTUCE_CROP);
	public static final DeferredItem<Item> LETTUCE_SEED = REGISTRY.register("lettuce_seed", LettuceSeedItem::new);
	public static final DeferredItem<Item> LOCK_REMOVER = REGISTRY.register("lock_remover", LockRemoverItem::new);
	public static final DeferredItem<Item> SINK_BLOCK = block(LoikvyModBlocks.SINK_BLOCK);
	public static final DeferredItem<Item> ANTIDEPRESSANTS = REGISTRY.register("antidepressants", AntidepressantsItem::new);
	public static final DeferredItem<Item> SHOP_CASHIER_SPAWN_EGG = REGISTRY.register("shop_cashier_spawn_egg", () -> new DeferredSpawnEggItem(LoikvyModEntities.SHOP_CASHIER, -1, -16750900, new Item.Properties()));
	public static final DeferredItem<Item> WEIGHT_SMALL_ONE_HANDED = REGISTRY.register("weight_small_one_handed", WeightSmallOneHandedItem::new);
	public static final DeferredItem<Item> GAMBLING_SLOTS_MACHINE = block(LoikvyModBlocks.GAMBLING_SLOTS_MACHINE);
	public static final DeferredItem<Item> BRIGHTER_TORCH = block(LoikvyModBlocks.BRIGHTER_TORCH);
	public static final DeferredItem<Item> GAMBLING_BLACK_JACK_TABLE = block(LoikvyModBlocks.GAMBLING_BLACK_JACK_TABLE);
	public static final DeferredItem<Item> RIPPED_SHEET = REGISTRY.register("ripped_sheet", RippedSheetItem::new);
	public static final DeferredItem<Item> WHEELCHAIR_SPAWN_EGG = REGISTRY.register("wheelchair_spawn_egg", () -> new DeferredSpawnEggItem(LoikvyModEntities.WHEELCHAIR, -16724788, -1, new Item.Properties()));
	public static final DeferredItem<Item> WIFI_ROUTER = block(LoikvyModBlocks.WIFI_ROUTER);

	// Start of user code block custom items
	// End of user code block custom items
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> new PhoneInventoryCapability(stack), PHONE.get());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}

	@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ItemsClientSideHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public static void clientLoad(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ItemProperties.register(BASIC_CLOCK.get(), ResourceLocation.parse("loikvy:basic_clock_time"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) BasicClockPropertyValueProviderProcedure.execute());
				ItemProperties.register(PHONE.get(), ResourceLocation.parse("loikvy:phone_turned_on"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) PhonePropertyValueProviderProcedure.execute(itemStackToRender));
				ItemProperties.register(RIPPED_SHEET.get(), ResourceLocation.parse("loikvy:ripped_sheet_dirty"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) RippedSheetPropertyValueProviderProcedure.execute(itemStackToRender));
			});
		}
	}
}