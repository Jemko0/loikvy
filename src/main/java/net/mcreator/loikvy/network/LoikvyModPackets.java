package net.mcreator.loikvy.network;

import net.mcreator.loikvy.utility.networking.packets.RequestStoragePacket;
import net.mcreator.loikvy.utility.networking.packets.StorageDataPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "loikvy", bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModPackets {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                RequestStoragePacket.TYPE,
                RequestStoragePacket.STREAM_CODEC,
                RequestStoragePacket::handle
        );

        registrar.playToClient(
                StorageDataPacket.TYPE,
                StorageDataPacket.STREAM_CODEC,
                StorageDataPacket::handle
        );
    }
}