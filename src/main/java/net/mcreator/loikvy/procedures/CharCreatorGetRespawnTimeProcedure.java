package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class CharCreatorGetRespawnTimeProcedure {
	public static double execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return 0;
		return (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerDeathTime + 2400) - LoikvyModVariables.MapVariables.get(world).GlobalTicks;
	}
}