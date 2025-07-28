package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;

public class HealthMenuGetBandageStringProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandaged) {
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty > 50) {
				return "\u00A76Dirty Bandage";
			}
			return "\u00A72Bandaged";
		}
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(LoikvyModMobEffects.BLEEDING)) {
			return "\u00A74Bleeding";
		}
		return "\u00A72Healthy";
	}
}