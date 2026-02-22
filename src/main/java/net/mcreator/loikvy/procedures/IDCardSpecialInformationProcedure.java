package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class IDCardSpecialInformationProcedure {
	public static String execute(ItemStack itemstack) {
		return ("FIRST NAME: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("owner_first_name") + "\n") + ""
				+ ("LAST NAME: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("owner_last_name") + "\n")
				+ ("D.O.B.: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("owner_dob") + "\n")
				+ ("ID NUM: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("id_num") + "\n")
				+ ("EXPIRES: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("expiry") + "\n");
	}
}