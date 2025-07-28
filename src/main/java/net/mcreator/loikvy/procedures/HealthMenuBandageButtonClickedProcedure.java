package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class HealthMenuBandageButtonClickedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		ItemStack returnbandage = ItemStack.EMPTY;
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandaged) {
			returnbandage = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageItem.copy();
			{
				final String _tagName = "dirty";
				final double _tagValue = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty;
				CustomData.update(DataComponents.CUSTOM_DATA, returnbandage, tag -> tag.putDouble(_tagName, _tagValue));
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = returnbandage.copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerBandageItem = new ItemStack(Blocks.AIR).copy();
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerBandaged = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerBandageDirty = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}