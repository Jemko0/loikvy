package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class BurntFoodPlayerFinishesUsingItemProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), 1);
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerSickness = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerSickness + 50;
			_vars.syncPlayerVariables(entity);
		}
		PlayerGainHappinessProcedure.execute(entity, 15);
	}
}