package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;

public class RippedSheetRightclickedProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		PutOnBandageProcedure.execute(entity, itemstack);
	}
}