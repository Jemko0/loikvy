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

import net.mcreator.loikvy.procedures.SlotsIncreaseBetProcedure;
import net.mcreator.loikvy.procedures.SlotsDecreaseBetProcedure;
import net.mcreator.loikvy.procedures.HalveBetProcedure;
import net.mcreator.loikvy.procedures.GamblingSlotsRollClickedProcedure;
import net.mcreator.loikvy.procedures.DoubleBetProcedure;
import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record SlotsGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<SlotsGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "slots_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SlotsGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SlotsGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new SlotsGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<SlotsGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final SlotsGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			GamblingSlotsRollClickedProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			SlotsDecreaseBetProcedure.execute(world, x, y, z);
		}
		if (buttonID == 2) {

			SlotsIncreaseBetProcedure.execute(world, x, y, z);
		}
		if (buttonID == 3) {

			DoubleBetProcedure.execute(world, x, y, z);
		}
		if (buttonID == 4) {

			HalveBetProcedure.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		LoikvyMod.addNetworkMessage(SlotsGUIButtonMessage.TYPE, SlotsGUIButtonMessage.STREAM_CODEC, SlotsGUIButtonMessage::handleData);
	}
}