package net.mcreator.loikvy;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.util.Tuple;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModTabs;
import net.mcreator.loikvy.init.LoikvyModSounds;
import net.mcreator.loikvy.init.LoikvyModParticleTypes;
import net.mcreator.loikvy.init.LoikvyModMobEffects;
import net.mcreator.loikvy.init.LoikvyModMenus;
import net.mcreator.loikvy.init.LoikvyModItems;
import net.mcreator.loikvy.init.LoikvyModFluids;
import net.mcreator.loikvy.init.LoikvyModFluidTypes;
import net.mcreator.loikvy.init.LoikvyModEntities;
import net.mcreator.loikvy.init.LoikvyModBlocks;
import net.mcreator.loikvy.init.LoikvyModBlockEntities;
import net.mcreator.loikvy.init.LoikvyModAttributes;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

@Mod("loikvy")
public class LoikvyMod {
	public static final Logger LOGGER = LogManager.getLogger(LoikvyMod.class);
	public static final String MODID = "loikvy";

	public LoikvyMod(IEventBus modEventBus) {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		NeoForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::registerNetworking);
		LoikvyModSounds.REGISTRY.register(modEventBus);
		LoikvyModBlocks.REGISTRY.register(modEventBus);
		LoikvyModBlockEntities.REGISTRY.register(modEventBus);
		LoikvyModItems.REGISTRY.register(modEventBus);
		LoikvyModEntities.REGISTRY.register(modEventBus);
		LoikvyModTabs.REGISTRY.register(modEventBus);
		LoikvyModVariables.ATTACHMENT_TYPES.register(modEventBus);

		LoikvyModMobEffects.REGISTRY.register(modEventBus);
		LoikvyModMenus.REGISTRY.register(modEventBus);
		LoikvyModParticleTypes.REGISTRY.register(modEventBus);

		LoikvyModFluids.REGISTRY.register(modEventBus);
		LoikvyModFluidTypes.REGISTRY.register(modEventBus);
		LoikvyModAttributes.REGISTRY.register(modEventBus);
		// Start of user code block mod init
		// End of user code block mod init
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
	}

	public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(id, new NetworkMessage<>(reader, handler));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(MODID);
		MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler()));
		networkingRegistered = true;
	}

	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new Tuple<>(action, tick));
	}

	@SubscribeEvent
	public void tick(ServerTickEvent.Post event) {
		List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setB(work.getB() - 1);
			if (work.getB() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getA().run());
		workQueue.removeAll(actions);
	}
}