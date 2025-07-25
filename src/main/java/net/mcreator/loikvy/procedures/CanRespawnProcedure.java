package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class CanRespawnProcedure {
	public static boolean execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return false;
		return CharCreatorGetRespawnTimeProcedure.execute(world, entity) <= 0 && entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerLives != 0;
	}
}