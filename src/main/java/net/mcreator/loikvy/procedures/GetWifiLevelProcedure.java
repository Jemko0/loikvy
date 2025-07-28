package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

public class GetWifiLevelProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		double rounded = 0;
		rounded = Math.round((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("wifi_strength"));
		if (rounded == 0) {
			return 5;
		}
		return 5 - rounded;
	}
}