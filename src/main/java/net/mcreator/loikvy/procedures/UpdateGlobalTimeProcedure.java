package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.loikvy.network.LoikvyModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class UpdateGlobalTimeProcedure {
	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		double totalSeconds = 0;
		if (!world.isClientSide()) {
			LoikvyModVariables.MapVariables.get(world).GlobalTicks = LoikvyModVariables.MapVariables.get(world).GlobalTicks + 1;
			LoikvyModVariables.MapVariables.get(world).syncData(world);
		}
		LoikvyModVariables.gWorldTicks = world.dayTime();
		LoikvyModVariables.gWorldDays = LoikvyModVariables.gWorldTicks / 24000;
		LoikvyModVariables.gWorldMonths = LoikvyModVariables.gWorldDays / 30;
		LoikvyModVariables.gWorldYears = LoikvyModVariables.gWorldMonths / 12;
		LoikvyModVariables.gWorldHours = (LoikvyModVariables.gWorldDays - Math.floor(LoikvyModVariables.gWorldDays)) * 24;
		LoikvyModVariables.gWorldMinutes = (LoikvyModVariables.gWorldHours - Math.floor(LoikvyModVariables.gWorldHours)) * 60;
		LoikvyModVariables.gWorldSeconds = (LoikvyModVariables.gWorldMinutes - Math.floor(LoikvyModVariables.gWorldMinutes)) * 60;
	}
}