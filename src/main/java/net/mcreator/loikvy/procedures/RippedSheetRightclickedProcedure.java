package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class RippedSheetRightclickedProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerBandaged = true;
			_vars.syncPlayerVariables(entity);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerBandageDirty = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("dirty");
			_vars.syncPlayerVariables(entity);
		}
		itemstack.shrink(1);
	}
}