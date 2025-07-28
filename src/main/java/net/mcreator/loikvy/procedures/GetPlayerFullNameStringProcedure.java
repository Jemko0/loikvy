package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class GetPlayerFullNameStringProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName;
	}
}