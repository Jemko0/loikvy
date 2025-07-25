package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class RippedSheetSpecialInformationProcedure {
	public static String execute(ItemStack itemstack) {
		String prefix = "";
		prefix = "\u00A72";
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("dirty") > 25) {
			prefix = "\u00A7e";
		}
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("dirty") > 50) {
			prefix = "\u00A76";
		}
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("dirty") > 75) {
			prefix = "\u00A7c";
		}
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("dirty") > 100) {
			prefix = "\u00A74";
		}
		return prefix + "Dirty: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("dirty");
	}
}