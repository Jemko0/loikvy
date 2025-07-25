package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

import javax.annotation.Nullable;

import java.util.List;

@EventBusSubscriber(value = {Dist.CLIENT})
public class ItemTooltipProcedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		execute(event, event.getItemStack(), event.getToolTip());
	}

	public static void execute(ItemStack itemstack, List<Component> tooltip) {
		execute(null, itemstack, tooltip);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
		if (tooltip == null)
			return;
		double lastSemi = 0;
		double nextSemi = 0;
		if (!(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cooking_progress") == 0)) {
			tooltip.add((int) tooltip.size(),
					Component.literal(("\u00A76Cooking Progress: " + new java.text.DecimalFormat("##.##").format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cooking_progress")) + "/100")));
		}
		if (!(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("burning") == 0)) {
			tooltip.add((int) tooltip.size(), Component.literal(("\u00A74Burning: " + new java.text.DecimalFormat("##.##").format(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("burning")) + "/100")));
		}
		lastSemi = 0;
		if (!(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt")).equals("")) {
			while (nextSemi != -1 && lastSemi != -1) {
				if (lastSemi == 0) {
					nextSemi = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt")).indexOf(";", (int) lastSemi);
					if (!(nextSemi == -1)) {
						tooltip.add((int) tooltip.size(), Component.literal(((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt")).substring(0, (int) nextSemi))));
						lastSemi = 1;
					} else {
						tooltip.add((int) tooltip.size(), Component.literal((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt"))));
						lastSemi = -1;
					}
				}
				lastSemi = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt")).indexOf(";", (int) (lastSemi + 1));
				if (!(lastSemi == -1)) {
					nextSemi = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt")).indexOf(";", (int) (lastSemi + 1));
					if (!(nextSemi == -1)) {
						tooltip.add((int) tooltip.size(), Component.literal(((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt")).substring((int) (lastSemi + 1), (int) nextSemi))));
					} else {
						tooltip.add((int) tooltip.size(), Component.literal(((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("receipt")).substring((int) (lastSemi + 1)))));
					}
				}
			}
		}
	}
}