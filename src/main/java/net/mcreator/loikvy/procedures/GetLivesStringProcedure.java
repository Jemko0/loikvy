package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class GetLivesStringProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return "" + new java.text.DecimalFormat("##").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerLives);
	}
}