package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class CharCreatorGetRespawnTimeStringProcedure {
	public static String execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return "";
		return Math.floor(CharCreatorGetRespawnTimeProcedure.execute(world, entity) / 60) + "s";
	}
}