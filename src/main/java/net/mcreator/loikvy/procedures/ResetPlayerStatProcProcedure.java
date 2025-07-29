package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
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
		if (target instanceof LivingEntity _entity)
			_entity.setHealth(20);
		if (target instanceof Player _player)
			_player.getFoodData().setFoodLevel(20);
		if (target instanceof Player _player)
			_player.giveExperienceLevels(-(target instanceof Player _plr ? _plr.experienceLevel : 0));
		target.clearFire();
		HealthMenuBandageButtonClickedProcedure.execute(target);
		if (target instanceof Player _player)
			_player.getInventory().clearContent();
	}
}