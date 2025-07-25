package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class PlayerGainHappinessProcedure {
	public static void execute(Entity entity, double amount) {
		if (entity == null)
			return;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerHappiness = ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHappiness + amount);
			_vars.syncPlayerVariables(entity);
		}
	}
}