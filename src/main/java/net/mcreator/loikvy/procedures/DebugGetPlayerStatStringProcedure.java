package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class DebugGetPlayerStatStringProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return ("Hygene: " + new java.text.DecimalFormat("##.####").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene)) + " \\n "
				+ ("Endurance: " + new java.text.DecimalFormat("##.####").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEndurance)) + " \\n "
				+ ("Fitness: " + new java.text.DecimalFormat("##.####").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFitness)) + " \\n "
				+ ("Sickness: " + new java.text.DecimalFormat("##.####").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerSickness)) + " \\n "
				+ ("Lives: " + new java.text.DecimalFormat("##.####").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerLives)) + " \\n "
				+ ("Happiness: " + new java.text.DecimalFormat("##.####").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHappiness)) + " \\n "
				+ ("Energy: " + new java.text.DecimalFormat("##.####").format(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEnergy));
	}
}