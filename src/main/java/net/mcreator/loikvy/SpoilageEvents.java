package net.mcreator.loikvy;

import net.mcreator.loikvy.init.LoikvyModDataAttachments;
import net.mcreator.loikvy.init.LoikvyModItems;
import net.mcreator.loikvy.network.LoikvyModVariables;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SpoilageEvents {
	public SpoilageEvents() {
	}

	private static final int SPOIL_RATE = 20 * 10;

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

			// Prepared/cooked food
			SPOILAGE_DAYS.put(LoikvyModItems.BURGER.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_BURGER.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.PEPPERONI_PIZZA.get(), 3);
			SPOILAGE_DAYS.put(LoikvyModItems.PIZZA_SLICE.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_PIZZA.get(), 3);
			SPOILAGE_DAYS.put(LoikvyModItems.CHEESE_PIZZA_SLICE.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.COOKED_NOODLES.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.DOUGH.get(), 3);

			// Unbaked
			SPOILAGE_DAYS.put(LoikvyModItems.UNBAKED_PEPPERONI_PIZZA.get(), 2);
			SPOILAGE_DAYS.put(LoikvyModItems.UNBAKED_CHEESE_PIZZA.get(), 2);

			// Dry goods
			SPOILAGE_DAYS.put(LoikvyModItems.FLOUR.get(), 30);
			SPOILAGE_DAYS.put(LoikvyModItems.WHEAT_FLOUR.get(), 30);
			SPOILAGE_DAYS.put(LoikvyModItems.UNCOOKED_NOODLES.get(), 30);

			// Drinks/packaged
			SPOILAGE_DAYS.put(LoikvyModItems.ENERGY_DRINK.get(), 60);
			SPOILAGE_DAYS.put(LoikvyModItems.SUNFLOWER_OIL.get(), 60);

			// Vanilla
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

	private static Map<ResourceLocation, Float> CONTAINER_SPOILAGE_MULTIPLIER = null;

	private static Map<ResourceLocation, Float> getContainerMultiplier()
	{
		if (CONTAINER_SPOILAGE_MULTIPLIER == null)
		{
			CONTAINER_SPOILAGE_MULTIPLIER = new HashMap<>();
			CONTAINER_SPOILAGE_MULTIPLIER.put(ResourceLocation.parse("loikvy:fridge"), 3.0f);
			CONTAINER_SPOILAGE_MULTIPLIER.put(ResourceLocation.parse("loikvy:freezer"), 10.0f);
		}
		return CONTAINER_SPOILAGE_MULTIPLIER;
	}

	private static Set<Item> NEVER_SPOIL = null;

	private static Set<Item> getNeverSpoil()
	{
		if (NEVER_SPOIL == null)
		{
			NEVER_SPOIL = new HashSet<>();
			NEVER_SPOIL.add(LoikvyModItems.ROTTEN_FOOD.get());
			NEVER_SPOIL.add(LoikvyModItems.BURNT_FOOD.get());
		}
		return NEVER_SPOIL;
	}

	public static boolean GetShouldSpoil(Item item)
	{
		return !getNeverSpoil().contains(item);
	}

	// Max spoilage is always 100.0 - item spoils when it reaches 100
	// Each check increments by (100 / totalChecksToSpoil) / multiplier
	// totalChecksToSpoil = (days * dayLengthTicks) / SPOIL_RATE
	private static float getSpoilageIncrement(ItemStack stack, float multiplier, ServerLevel level)
	{
		int days = getSpoilageDays().getOrDefault(stack.getItem(), 3);
		long dayLengthTicks = LoikvyJavaUtil.GetDayLengthInTicks(level);
		float totalChecks = (days * dayLengthTicks) / (float) SPOIL_RATE;
		return (100.0f / totalChecks) / multiplier;
	}

	// Returns 0.0 - 100.0, or -1 if not yet tracked
	public static float GetSpoilagePercent(ItemStack stack)
	{
		Float spoilage = stack.get(LoikvyModDataAttachments.SPOILAGE.get());
		if (spoilage == null) return -1.0f;
		return spoilage;
	}

	private static float getBlockEntityMultiplier(BlockEntity be)
	{
		ResourceLocation location = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(be.getType());
		if (location == null) return 1.0f;
		return getContainerMultiplier().getOrDefault(location, 1.0f);
	}

	private static void spoilItem(ItemStack stack, float multiplier, IItemHandler handler, int slot, ServerLevel level)
	{
		if (stack.isEmpty()) return;
		if (stack.getItem().getFoodProperties(stack, null) == null) return;
		if (!GetShouldSpoil(stack.getItem())) return;

		float spoilage = stack.getOrDefault(LoikvyModDataAttachments.SPOILAGE.get(), 0.0f);
		spoilage += getSpoilageIncrement(stack, multiplier, level);
		stack.set(LoikvyModDataAttachments.SPOILAGE.get(), spoilage);

		if (spoilage >= 100.0f)
		{
			int count = stack.getCount();
			stack.setCount(0);
			handler.insertItem(slot, new ItemStack(LoikvyModItems.ROTTEN_FOOD.get(), count), false);
		}
	}

	private static final java.util.concurrent.ConcurrentHashMap<ServerLevel, Set<BlockPos>> TRACKED_POSITIONS = new java.util.concurrent.ConcurrentHashMap<>();

	private static Set<BlockPos> getPositions(ServerLevel level)
	{
		return TRACKED_POSITIONS.computeIfAbsent(level, k -> new HashSet<>());
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
		public static void serverLoad(ServerStartingEvent event) {
		}

		@SubscribeEvent
		public static void onChunkLoad(net.neoforged.neoforge.event.level.ChunkEvent.Load event)
		{
			if (!(event.getLevel() instanceof ServerLevel level)) return;
			if (!(event.getChunk() instanceof net.minecraft.world.level.chunk.LevelChunk chunk)) return;
			getPositions(level).addAll(chunk.getBlockEntities().keySet());
		}

		@SubscribeEvent
		public static void onChunkUnload(net.neoforged.neoforge.event.level.ChunkEvent.Unload event)
		{
			if (!(event.getLevel() instanceof ServerLevel level)) return;
			if (!(event.getChunk() instanceof net.minecraft.world.level.chunk.LevelChunk chunk)) return;
			getPositions(level).removeAll(chunk.getBlockEntities().keySet());
		}

		@SubscribeEvent
		public static void onLevelTick(LevelTickEvent.Post event)
		{
			if (!(event.getLevel() instanceof ServerLevel level)) return;
			if (level.dimension() != Level.OVERWORLD) return;
			if (level.getGameTime() % SPOIL_RATE != 0) return;

			for (BlockPos pos : new HashSet<>(getPositions(level)))
			{
				IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
				if (handler == null || handler.getSlots() == 0) continue;

				BlockEntity be = level.getBlockEntity(pos);
				if (be == null) continue;

				float multiplier = getBlockEntityMultiplier(be);

				for (int i = 0; i < handler.getSlots(); i++)
				{
					ItemStack stack = handler.getStackInSlot(i);
					if (!stack.isEmpty())
					{
						spoilItem(stack, multiplier, handler, i, level);
					}
				}
			}
		}

		@SubscribeEvent
		public static void onPlayerTick(PlayerTickEvent.Post event)
		{
			if (!(event.getEntity() instanceof ServerPlayer player)) return;
			if (player.level().getGameTime() % SPOIL_RATE != 0) return;

			ServerLevel level = (ServerLevel) player.level();

			for (int i = 0; i < player.getInventory().getContainerSize(); i++)
			{
				ItemStack stack = player.getInventory().getItem(i);
				if (!stack.isEmpty())
				{
					final int slotIndex = i;
					IItemHandler fakeHandler = new IItemHandler() {
						public int getSlots() { return player.getInventory().getContainerSize(); }
						public ItemStack getStackInSlot(int slot) { return player.getInventory().getItem(slot); }
						public ItemStack insertItem(int slot, ItemStack s, boolean sim) {
							if (!sim) player.getInventory().setItem(slot, s);
							return ItemStack.EMPTY;
						}
						public ItemStack extractItem(int slot, int amount, boolean sim) { return ItemStack.EMPTY; }
						public int getSlotLimit(int slot) { return 64; }
						public boolean isItemValid(int slot, ItemStack s) { return true; }
					};

					spoilItem(stack, 1.0f, fakeHandler, slotIndex, level);
				}
			}

			player.inventoryMenu.broadcastChanges();
		}

		@SubscribeEvent
		public static void onItemTooltip(ItemTooltipEvent event)
		{
			ItemStack stack = event.getItemStack();
			if (stack.isEmpty() || stack.getItem().getFoodProperties(stack, null) == null) return;
			if (!GetShouldSpoil(stack.getItem())) return;

			float spoilage = GetSpoilagePercent(stack);
			if (spoilage < 0.0f) return; // not yet tracked

			if (spoilage >= 100.0f)
			{
				event.getToolTip().add(Component.literal("Spoiled").withStyle(ChatFormatting.RED));
			}
			else if (spoilage >= 50.0f)
			{
				event.getToolTip().add(Component.literal("Stale").withStyle(ChatFormatting.YELLOW));
			}
			else
			{
				event.getToolTip().add(Component.literal("Fresh").withStyle(ChatFormatting.GREEN));
			}

			if (event.getEntity() != null && event.getEntity().canUseGameMasterBlocks())
			{
				event.getToolTip().add(Component.literal(
						String.format("Spoilage: %.1f%%", spoilage)
				).withStyle(ChatFormatting.GRAY));
			}
		}
	}
}