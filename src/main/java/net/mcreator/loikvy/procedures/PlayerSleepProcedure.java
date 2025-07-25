package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModGameRules;
import net.mcreator.loikvy.LoikvyMod;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerSleepProcedure {
	@SubscribeEvent
	public static void onPlayerInBed(CanPlayerSleepEvent event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		boolean pendingDream = false;
		pendingDream = (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerAlwaysDream || Mth.nextDouble(RandomSource.create(), 1, 100) >= 101) && world.getLevelData().getGameRules().getBoolean(LoikvyModGameRules.DO_DREAMS);
		if (pendingDream) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerPreDreamHealth = entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1;
				_vars.syncPlayerVariables(entity);
			}
			LoikvyMod.queueServerWork((int) Mth.nextDouble(RandomSource.create(), 40, 80), () -> {
				if (entity instanceof LivingEntity _livEnt4 && _livEnt4.isSleeping() && !entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerInDream) {
					{
						LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
						_vars.gPlayerInDream = true;
						_vars.syncPlayerVariables(entity);
					}
					ProcessDreamProcedure.execute(world, entity, Mth.nextInt(RandomSource.create(), 0, 3));
				}
			});
		}
	}
}