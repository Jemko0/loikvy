package net.mcreator.loikvy.utility.networking.packets;

import net.mcreator.loikvy.utility.networking.interfaces.IStorageReceiver;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.client.Minecraft;

// Response packet (Server → Client)
public record StorageDataPacket(ResourceLocation storageId, CompoundTag data) implements CustomPacketPayload {

    public static final Type<StorageDataPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("loikvy", "storage_data"));

    public static final StreamCodec<FriendlyByteBuf, StorageDataPacket> STREAM_CODEC =
            StreamCodec.ofMember(StorageDataPacket::write, StorageDataPacket::read);

    @Override
    public Type<StorageDataPacket> type() {
        return TYPE;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(storageId);
        buf.writeNbt(data);
    }

    public static StorageDataPacket read(FriendlyByteBuf buf) {
        return new StorageDataPacket(
                buf.readResourceLocation(),
                buf.readNbt()
        );
    }

    public static void handle(StorageDataPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().screen instanceof IStorageReceiver screen) {
                screen.receiveStorageData(packet.data);
            }
        });
    }
}