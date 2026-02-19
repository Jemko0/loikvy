/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.loikvy as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.loikvy;

import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import net.minecraft.server.level.ServerLevel;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyJavaUtil {
	public LoikvyJavaUtil() {
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		new LoikvyJavaUtil();
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
	}

	@EventBusSubscriber
	private static class LoikvyJavaUtilForgeBusEvents {
		@SubscribeEvent
		public static void serverLoad(ServerStartingEvent event) {
		}
	}

	public static long GetDayLengthInTicks(ServerLevel level)
	{
		float speed = level.getDayTimePerTick();

		// Handle default (vanilla = 24000 ticks)
		long dayLengthTicks = speed < 0 ? 24000 : (long)(24000f / speed);

		return dayLengthTicks;
	}

	public static long GetDayLengthInTicks()
	{
		ClientLevel level = net.minecraft.client.Minecraft.getInstance().level;
		if (level == null) return 24000L;
		float speed = level.getDayTimePerTick();
		return speed < 0 ? 24000 : (long)(24000f / speed);
	}
}