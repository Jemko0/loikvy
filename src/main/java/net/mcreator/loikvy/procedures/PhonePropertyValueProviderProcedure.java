package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class PhonePropertyValueProviderProcedure {
	public static double execute(ItemStack itemstack) {
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("on")) {
			return 1;
		}
		return 0;
	}
}