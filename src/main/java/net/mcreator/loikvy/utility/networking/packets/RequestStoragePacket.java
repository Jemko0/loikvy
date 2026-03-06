package net.mcreator.loikvy.utility.networking.packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;

// Request packet (Client → Server)
public record RequestStoragePacket(ResourceLocation storageId) implements CustomPacketPayload {

    public static final Type<RequestStoragePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("loikvy", "request_storage"));

    public static final StreamCodec<FriendlyByteBuf, RequestStoragePacket> STREAM_CODEC =
            StreamCodec.ofMember(RequestStoragePacket::write, RequestStoragePacket::read);

    @Override
    public Type<RequestStoragePacket> type() {
        return TYPE;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(storageId);
    }

    public static RequestStoragePacket read(FriendlyByteBuf buf) {
        return new RequestStoragePacket(buf.readResourceLocation());
    }

    public static void handle(RequestStoragePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                var commandStorage = serverPlayer.getServer().getCommandStorage();
                CompoundTag data = commandStorage.get(packet.storageId);

                // Send response back to client
                PacketDistributor.sendToPlayer(serverPlayer,
                        new StorageDataPacket(packet.storageId, data != null ? data : new CompoundTag()));
            }
        });
    }
}