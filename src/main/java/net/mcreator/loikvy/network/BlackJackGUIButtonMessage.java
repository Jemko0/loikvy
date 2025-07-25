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

import net.mcreator.loikvy.procedures.StartBlackJackProcedure;
import net.mcreator.loikvy.procedures.SlotsIncreaseBetProcedure;
import net.mcreator.loikvy.procedures.SlotsDecreaseBetProcedure;
import net.mcreator.loikvy.procedures.HalveBetProcedure;
import net.mcreator.loikvy.procedures.DoubleBetProcedure;
import net.mcreator.loikvy.procedures.BlackjackStandProcedure;
import net.mcreator.loikvy.procedures.BlackjackHitProcedure;
import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record BlackJackGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<BlackJackGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "black_jack_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, BlackJackGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, BlackJackGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new BlackJackGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<BlackJackGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final BlackJackGUIButtonMessage message, final IPayloadContext context) {
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

			BlackjackHitProcedure.execute(world, x, y, z);
		}
		if (buttonID == 1) {

			BlackjackStandProcedure.execute(world, x, y, z);
		}
		if (buttonID == 2) {

			StartBlackJackProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 3) {

			HalveBetProcedure.execute(world, x, y, z);
		}
		if (buttonID == 4) {

			SlotsDecreaseBetProcedure.execute(world, x, y, z);
		}
		if (buttonID == 5) {

			DoubleBetProcedure.execute(world, x, y, z);
		}
		if (buttonID == 6) {

			SlotsIncreaseBetProcedure.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		LoikvyMod.addNetworkMessage(BlackJackGUIButtonMessage.TYPE, BlackJackGUIButtonMessage.STREAM_CODEC, BlackJackGUIButtonMessage::handleData);
	}
}