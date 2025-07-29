package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class BleedingOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (LoikvyModVariables.MapVariables.get(world).GlobalTicks % 150 <= 8) {
			if (!entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandaged) {
				entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.IN_WALL), entity), 1);
			}
		}
	}
}