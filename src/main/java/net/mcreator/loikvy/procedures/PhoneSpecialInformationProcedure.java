package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class PhoneSpecialInformationProcedure {
	public static String execute(ItemStack itemstack) {
		return "Model: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("model_name");
	}
}