package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

public class GetPlayerNameFromCorpseProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return entity.getPersistentData().getString("PlayerName");
	}
}