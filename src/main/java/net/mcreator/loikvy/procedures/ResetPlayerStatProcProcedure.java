package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class ResetPlayerStatProcProcedure {
	public static void execute(Entity entity, Entity target) {
		if (entity == null || target == null)
			return;
		{
			LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerHygene = 100;
			_vars.syncPlayerVariables(target);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerEndurance = 100;
			_vars.syncPlayerVariables(target);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerFitness = 5;
			_vars.syncPlayerVariables(target);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerSickness = 0;
			_vars.syncPlayerVariables(target);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerEnergy = 100;
			_vars.syncPlayerVariables(target);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerLives = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerLives - 1;
			_vars.syncPlayerVariables(target);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerHappiness = 60;
			_vars.syncPlayerVariables(target);
		}
	}
}