package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class SetMasterLockProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerCurrentDoorID = "master_lock";
			_vars.syncPlayerVariables(entity);
		}
	}
}