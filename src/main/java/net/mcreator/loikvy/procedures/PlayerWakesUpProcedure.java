package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerWakesUpProcedure {
	@SubscribeEvent
	public static void onEntityEndSleep(PlayerWakeUpEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerHappiness = ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHappiness + 10);
			_vars.syncPlayerVariables(entity);
		}
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerEnergy = 100;
			_vars.syncPlayerVariables(entity);
		}
	}
}