package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class DrunkOnEffectActiveTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getDeltaMovement().x() > 0 && entity.getDeltaMovement().z() > 0) {
			entity.push((Mth.nextDouble(RandomSource.create(), -1, 1)), 0, (Mth.nextDouble(RandomSource.create(), -1, 1)));
		}
	}
}