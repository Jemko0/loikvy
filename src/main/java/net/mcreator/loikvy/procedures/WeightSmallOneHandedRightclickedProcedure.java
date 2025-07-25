package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;

public class WeightSmallOneHandedRightclickedProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (!(entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem()))) {
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(LoikvyModMobEffects.EXERTION))) {
				{
					LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
					_vars.gPlayerFitness = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFitness + 0.1;
					_vars.syncPlayerVariables(entity);
				}
				{
					LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
					_vars.gPlayerEndurance = ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEndurance - 75 * GetFitnessMultiplierProcedure.execute(entity));
					_vars.syncPlayerVariables(entity);
				}
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), (int) (100 * GetFitnessMultiplierProcedure.execute(entity)));
			}
		}
	}
}