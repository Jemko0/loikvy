package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModItems;

public class EnergyDrinkPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		ItemStack emptycan = ItemStack.EMPTY;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerSickness = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerSickness + 5;
			_vars.syncPlayerVariables(entity);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerFitness = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFitness - 0.1;
			_vars.syncPlayerVariables(entity);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerEnergy = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEnergy + 5;
			_vars.syncPlayerVariables(entity);
		}
		emptycan = new ItemStack(LoikvyModItems.EMPTY_CAN.get()).copy();
		{
			final String _tagName = "type";
			final double _tagValue = 1;
			CustomData.update(DataComponents.CUSTOM_DATA, emptycan, tag -> tag.putDouble(_tagName, _tagValue));
		}
		if (entity instanceof Player _player) {
			ItemStack _setstack = emptycan.copy();
			_setstack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
		}
	}
}