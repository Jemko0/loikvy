/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.loikvy as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.loikvy;

import net.mcreator.loikvy.init.LoikvyModBlocks;
import net.mcreator.loikvy.init.LoikvyModDataAttachments;
import net.mcreator.loikvy.init.LoikvyModItems;
import net.mcreator.loikvy.network.LoikvyModVariables;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.wrapper.PlayerInvWrapper;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SpoilageEvents {
	public SpoilageEvents() {
	}

	private static Map<Item, Integer> SPOILAGE_DAYS = null;

	private static Map<Item, Integer> getSpoilageDays()
	{
		if (SPOILAGE_DAYS == null)
		{
			SPOILAGE_DAYS = new HashMap<>();

			// Produce - spoils fast
			SPOILAGE_DAYS.put(LoikvyModItems.TOMATO.get(), 3);
			SPOILAGE_DAYS.put(LoikvyModItems.TOMATO_SLICE.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.LETTUCE.get(), 3);
			SPOILAGE_DAYS.put(LoikvyModItems.LETTUCE_LEAF.get(), 2);

			// Dairy
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_WHEEL.get(), 14);
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_SLICE.get(), 7);

			// Meat/charcuterie
			SPOILAGE_DAYS.put(LoikvyModItems.PEPPERONI_CHUNK.get(), 7);
			SPOILAGE_DAYS.put(LoikvyModItems.PEPPERONI_SLICE.get(), 5);

			// Prepared/cooked food - spoils faster
			SPOILAGE_DAYS.put(LoikvyModItems.BURGER.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_BURGER.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.PEPPERONI_PIZZA.get(), 3);
			SPOILAGE_DAYS.put(LoikvyModItems.PIZZA_SLICE.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_PIZZA.get(), 3);
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_PIZZA_SLICE.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.COOKED_NOODLES.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.DOUGH.get(), 3);

			// Unbaked - treat like dough
			SPOILAGE_DAYS.put(LoikvyModItems.UNBAKED_PEPPERONI_PIZZA.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.UNBAKED_CHEESE_PIZZA.get(), 2);

			// Dry goods - long shelf life
			SPOILAGE_DAYS.put(LoikvyModItems.FLOUR.get(), 30);
			SPOILAGE_DAYS.put(LoikvyModItems.WHEAT_FLOUR.get(), 30);
			SPOILAGE_DAYS.put(LoikvyModItems.UNCOOKED_NOODLES.get(), 30);

			// Drinks/packaged - very long
			SPOILAGE_DAYS.put(LoikvyModItems.ENERGY_DRINK.get(), 60);
			SPOILAGE_DAYS.put(LoikvyModItems.SUNFLOWER_OIL.get(), 60);

			// Vanilla food items
			SPOILAGE_DAYS.put(Items.BREAD, 5);
			SPOILAGE_DAYS.put(Items.APPLE, 5);
			SPOILAGE_DAYS.put(Items.COOKED_BEEF, 3);
			SPOILAGE_DAYS.put(Items.BEEF, 2);
			SPOILAGE_DAYS.put(Items.COOKED_CHICKEN, 3);
			SPOILAGE_DAYS.put(Items.CHICKEN, 2);
			SPOILAGE_DAYS.put(Items.COOKED_PORKCHOP, 3);
			SPOILAGE_DAYS.put(Items.PORKCHOP, 2);
			SPOILAGE_DAYS.put(Items.COOKED_MUTTON, 3);
			SPOILAGE_DAYS.put(Items.MUTTON, 2);
			SPOILAGE_DAYS.put(Items.EGG, 7);
			SPOILAGE_DAYS.put(Items.COOKIE, 7);
			SPOILAGE_DAYS.put(Items.CAKE, 4);
			SPOILAGE_DAYS.put(Items.CARROT, 7);
			SPOILAGE_DAYS.put(Items.POTATO, 10);
			SPOILAGE_DAYS.put(Items.BAKED_POTATO, 3);
			SPOILAGE_DAYS.put(Items.MUSHROOM_STEW, 2);
			SPOILAGE_DAYS.put(Items.MELON_SLICE, 3);
			SPOILAGE_DAYS.put(Items.SWEET_BERRIES, 3);
		}
		return SPOILAGE_DAYS;
	}

	private static Map<Block, Float> CONTAINER_SPOILAGE_MULTIPLIER = null;

	private static Map<Block, Float> getContainerMultiplier()
	{
		if (CONTAINER_SPOILAGE_MULTIPLIER == null)
		{
			CONTAINER_SPOILAGE_MULTIPLIER = new HashMap<>();
			CONTAINER_SPOILAGE_MULTIPLIER.put(LoikvyModBlocks.FRIDGE.get(), 3.0f);
			CONTAINER_SPOILAGE_MULTIPLIER.put(LoikvyModBlocks.FREEZER.get(), 10.0f);
		}
		return CONTAINER_SPOILAGE_MULTIPLIER;
	}

	private static Set<Item> CONTAINER_NEVER_SPOIL = null;

	private static Set<Item> getNeverSpoil()
	{
		if (CONTAINER_NEVER_SPOIL == null)
		{
			CONTAINER_NEVER_SPOIL = new HashSet<>();
			CONTAINER_NEVER_SPOIL.add(LoikvyModItems.ROTTEN_FOOD.get());
			CONTAINER_NEVER_SPOIL.add(LoikvyModItems.BURNT_FOOD.get());
		}
		return CONTAINER_NEVER_SPOIL;
	}

	public static long GetSpoilageTicksForItem(ItemStack stack, long dayLengthTicks)
	{
		int days = getSpoilageDays().getOrDefault(stack.getItem(), 3);
		return days * dayLengthTicks;
	}

	public static float GetContainerSpoilageMultiplier(AbstractContainerMenu container)
	{
		if (container instanceof InventoryMenu) return 1.0f;

		if (container instanceof AbstractFurnaceMenu) return 1.0f;

		// for block entity containers, check the block type
		for (Slot slot : container.slots) {
			if (slot.container instanceof BaseContainerBlockEntity be) {
				Block block = be.getLevel().getBlockState(be.getBlockPos()).getBlock();
				return getContainerMultiplier().getOrDefault(block, 1.0f);
			}
			break;
		}

		return 1.0f;
	}

	public static boolean GetShouldSpoil(Item item)
	{
		return !getNeverSpoil().contains(item);
	}

	public static long GetSpoilageRemainingTime(ItemStack stack)
	{
		if (stack.isEmpty() || stack.getItem().getFoodProperties(stack, null) == null) return -1L;

		long created = stack.getOrDefault(LoikvyModDataAttachments.CREATION_TIME.get(), -1L);
		if (created == -1L) return -1L;

		long elapsed = (long) LoikvyModVariables.gWorldTicks - created;
		long spoilTicks = GetSpoilageTicksForItem(stack, LoikvyJavaUtil.GetDayLengthInTicks());

		return spoilTicks - elapsed;
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		new SpoilageEvents();
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
	}

	@EventBusSubscriber
	private static class SpoilageEventsForgeBusEvents {
		@SubscribeEvent
		public static void serverLoad(ServerStartingEvent event)
		{
		}

		@SubscribeEvent
		public static void onContainerOpened(PlayerContainerEvent.Open event) {
			Player player = event.getEntity();
			AbstractContainerMenu menu = event.getContainer();

			if (player.level().isClientSide()) return;

			ServerLevel level = (ServerLevel) player.level();

			for (int i = 0; i < menu.slots.size(); i++)
			{
				Slot slot = menu.getSlot(i);
				ItemStack stack = slot.getItem();
				if (!stack.isEmpty() && stack.getItem().getFoodProperties(stack, null) != null)
				{
					if(GetShouldSpoil(stack.getItem()))
					{
						checkSpoilage(stack, level, menu, player);
					}
				}
			}
		}

		@SubscribeEvent
		public static void onPlayerTick(PlayerTickEvent.Post event) {
			if (event.getEntity().level().isClientSide()) return;
			if (event.getEntity().tickCount % (20 * 15) != 0) return; // check every 15 seconds

			Player player = event.getEntity();
			ServerLevel level = (ServerLevel) player.level();

			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				if (!stack.isEmpty() && stack.getItem().getFoodProperties(stack, null) != null)
				{
					if(GetShouldSpoil(stack.getItem()))
					{
						checkSpoilage(stack, level, player.inventoryMenu, player);
					}
				}
			}
		}

		@SubscribeEvent
		public static void onItemTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (stack.isEmpty() || stack.getItem().getFoodProperties(stack, null) == null) return;

			long spoilTicks = GetSpoilageTicksForItem(stack, LoikvyJavaUtil.GetDayLengthInTicks());
			long remaining = GetSpoilageRemainingTime(stack);

			if (remaining == -1L)
			{
				return;
			}

			if (remaining <= 0)
			{
				event.getToolTip().add(Component.literal("Spoiled").withStyle(ChatFormatting.RED));
			}
			else if (remaining < spoilTicks / 4)
			{
				event.getToolTip().add(Component.literal("Stale").withStyle(ChatFormatting.YELLOW));
			}
			else
			{
				event.getToolTip().add(Component.literal("Fresh").withStyle(ChatFormatting.GREEN));
			}
		}
	}

	private static void checkSpoilage(ItemStack stack, ServerLevel level, AbstractContainerMenu container, Player player)
	{
		long created = stack.getOrDefault(LoikvyModDataAttachments.CREATION_TIME.get(), -1L);
		if (created == -1L)
		{
			stack.set(LoikvyModDataAttachments.CREATION_TIME.get(), (long) LoikvyModVariables.gWorldTicks);
			return;
		}

		long elapsed = (long) LoikvyModVariables.gWorldTicks - created;
		float multiplier = GetContainerSpoilageMultiplier(container);
		long spoilTicks = GetSpoilageTicksForItem(stack, (long)((float) LoikvyJavaUtil.GetDayLengthInTicks(level) * multiplier));

		if (elapsed > spoilTicks)
		{
			ItemStack spoiled = new ItemStack(LoikvyModItems.ROTTEN_FOOD.get());
			stack.shrink(1);

			if(!stack.isEmpty())
			{
				stack.set(LoikvyModDataAttachments.CREATION_TIME.get(), (long) LoikvyModVariables.gWorldTicks);
			}

			player.getInventory().add(spoiled);

			container.broadcastChanges();
		}
	}
}