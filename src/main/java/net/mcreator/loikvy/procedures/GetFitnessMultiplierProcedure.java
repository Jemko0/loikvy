package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class GetFitnessMultiplierProcedure {
	public static double execute(Entity target) {
		if (target == null)
			return 0;
		return (110 - target.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFitness) / 100;
	}
}