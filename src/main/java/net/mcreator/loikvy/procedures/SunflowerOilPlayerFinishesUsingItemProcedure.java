package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;

public class SunflowerOilPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerSickness = 100;
			_vars.syncPlayerVariables(entity);
		}
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(LoikvyModMobEffects.SICK)) {
			if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(LoikvyModMobEffects.WET) ? _livEnt.getEffect(LoikvyModMobEffects.WET).getAmplifier() : 0) == 2) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.WET, 18000, 3));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.FOOD_POISONING, 18000, 3));
			}
			if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(LoikvyModMobEffects.WET) ? _livEnt.getEffect(LoikvyModMobEffects.WET).getAmplifier() : 0) == 1) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.WET, 18000, 2));
			}
			if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(LoikvyModMobEffects.WET) ? _livEnt.getEffect(LoikvyModMobEffects.WET).getAmplifier() : 0) == 0) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.WET, 18000, 1));
			}
		}
	}
}