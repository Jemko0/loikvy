package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;

public class AntidepressantsPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		PlayerGainHappinessProcedure.execute(entity, 50);
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerSickness = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerSickness + 20;
			_vars.syncPlayerVariables(entity);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerEnergy = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEnergy - 15;
			_vars.syncPlayerVariables(entity);
		}
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(LoikvyModMobEffects.OVERSTIMULATED)) {
			PlayerGainHappinessProcedure.execute(entity, -100);
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerSickness = 100;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerEnergy = 0;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.OVERDOSING, 1200, 0, false, false));
		}
		if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(LoikvyModMobEffects.STIMULATED)) {
			if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(LoikvyModMobEffects.STIMULATED) ? _livEnt.getEffect(LoikvyModMobEffects.STIMULATED).getAmplifier() : 0) > 0) {
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(LoikvyModMobEffects.STIMULATED);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.OVERSTIMULATED, 9600, 0, false, false));
			} else {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.STIMULATED, 600, 1, false, false));
			}
		} else {
			if (!(entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(LoikvyModMobEffects.OVERSTIMULATED))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.STIMULATED, 600, 0, false, false));
			}
		}
	}
}