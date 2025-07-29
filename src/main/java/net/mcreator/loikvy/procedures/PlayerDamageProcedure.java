package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerDamageProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, double amount) {
		execute(null, damagesource, entity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		double damageMultiplier = 0;
		if (entity instanceof Player) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.last_damage_type = "" + damagesource;
				_vars.syncPlayerVariables(entity);
			}
			damageMultiplier = 1;
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsTough) {
				damageMultiplier = 0.75;
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsProneToInjury) {
				damageMultiplier = 2.5;
			}
			if (!entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gDamageCancelled) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				{
					LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
					_vars.gDamageCancelled = true;
					_vars.syncPlayerVariables(entity);
				}
				entity.hurt(damagesource, (float) (amount * damageMultiplier));
			}
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 200, 0));
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gDamageCancelled = false;
				_vars.syncPlayerVariables(entity);
			}
			if (amount > 5) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.BLEEDING, (int) (30 * 20 * (amount / 20)), 0));
			}
			if (amount > 8) {
				if (Mth.nextInt(RandomSource.create(), 1, 100) > 95) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PARALYZED, (int) Double.POSITIVE_INFINITY, 0));
				}
			}
		}
	}
}