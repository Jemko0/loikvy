package net.mcreator.loikvy.computer.network;

import net.mcreator.loikvy.LoikvyMod;
import net.mcreator.loikvy.computer.block.entity.ComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncComputerDataPayload(BlockPos pos, CompoundTag osData) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SyncComputerDataPayload> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "sync_os_data"));

    public static final StreamCodec<FriendlyByteBuf, SyncComputerDataPayload> STREAM_CODEC = StreamCodec.of(
            (buf, payload) -> {
                buf.writeBlockPos(payload.pos());
                buf.writeNbt(payload.osData());
            },
            buf -> new SyncComputerDataPayload(buf.readBlockPos(), buf.readNbt()));

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SyncComputerDataPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                Level level = player.level();
                if (level.isLoaded(payload.pos())) {
                    BlockEntity be = level.getBlockEntity(payload.pos());
                    if (be instanceof ComputerBlockEntity computer) {
                        // Prevent arbitrary long distance NBT editing (Hack prevention)
                        if (player.distanceToSqr(computer.getBlockPos().getX() + 0.5D,
                                computer.getBlockPos().getY() + 0.5D, computer.getBlockPos().getZ() + 0.5D) <= 64.0D) {
                            computer.setOsData(payload.osData());
                        }
                    }
                }
            }
        });
    }
}
