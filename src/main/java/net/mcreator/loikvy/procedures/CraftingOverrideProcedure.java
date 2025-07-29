package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

@EventBusSubscriber
public class CraftingOverrideProcedure {
	@SubscribeEvent
	public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		execute(event, event.getEntity().level(), event.getCrafting());
	}

	public static void execute(LevelAccessor world, ItemStack itemstack) {
		execute(null, world, itemstack);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, ItemStack itemstack) {
		double slot = 0;
		slot = 0;
		for (int index0 = 0; index0 < 8; index0++) {
			if (!world.isClientSide() && world.getServer() != null)
				world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("index " + slot + " // provided stack: " + itemstack)), false);
			slot = slot + 1;
		}
	}
}