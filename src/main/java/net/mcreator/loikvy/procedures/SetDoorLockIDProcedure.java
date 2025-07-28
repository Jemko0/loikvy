package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMenus;

public class SetDoorLockIDProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerCurrentDoorID = (entity instanceof Player _entity0 && _entity0.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "door_id", "") : "";
			_vars.syncPlayerVariables(entity);
		}
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}