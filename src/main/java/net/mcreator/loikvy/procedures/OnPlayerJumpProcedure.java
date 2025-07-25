package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class OnPlayerJumpProcedure {
	@SubscribeEvent
	public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerEndurance = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEndurance - 1 * GetFitnessMultiplierProcedure.execute(entity);
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}