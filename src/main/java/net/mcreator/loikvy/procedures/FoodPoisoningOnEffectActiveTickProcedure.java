package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;

public class FoodPoisoningOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double tick = 0;
		entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC_KILL)), 2);
	}
}