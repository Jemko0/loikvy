package net.mcreator.loikvy.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.procedures.UpdateSmokeDetectorBatteryStateProcedure;
import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record SmokeDetectorGUISlotMessage(int slotID, int x, int y, int z, int changeType, int meta) implements CustomPacketPayload {

	public static final Type<SmokeDetectorGUISlotMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "smoke_detector_gui_slots"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SmokeDetectorGUISlotMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SmokeDetectorGUISlotMessage message) -> {
		buffer.writeInt(message.slotID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		buffer.writeInt(message.changeType);
		buffer.writeInt(message.meta);
	}, (RegistryFriendlyByteBuf buffer) -> new SmokeDetectorGUISlotMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<SmokeDetectorGUISlotMessage> type() {
		return TYPE;
	}

	public static void handleData(final SmokeDetectorGUISlotMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleSlotAction(context.player(), message.slotID, message.changeType, message.meta, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleSlotAction(Player entity, int slot, int changeType, int meta, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (slot == 0 && changeType == 0) {

			UpdateSmokeDetectorBatteryStateProcedure.execute(world, x, y, z, entity);
		}
		if (slot == 0 && changeType == 1) {
			int amount = meta;

			UpdateSmokeDetectorBatteryStateProcedure.execute(world, x, y, z, entity);
		}
		if (slot == 0 && changeType == 2) {
			int amount = meta;

			UpdateSmokeDetectorBatteryStateProcedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		LoikvyMod.addNetworkMessage(SmokeDetectorGUISlotMessage.TYPE, SmokeDetectorGUISlotMessage.STREAM_CODEC, SmokeDetectorGUISlotMessage::handleData);
	}
}