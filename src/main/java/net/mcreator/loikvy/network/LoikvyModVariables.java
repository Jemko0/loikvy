package net.mcreator.loikvy.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.mcreator.loikvy.LoikvyMod;

import java.util.function.Supplier;
import java.util.ArrayList;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, LoikvyMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());
	public static double gWorldTicks = 0;
	public static double gWorldDays = 0;
	public static double gWorldMonths = 0;
	public static double gWorldYears = 0;
	public static double gWorldSeconds = 0;
	public static double gWorldMinutes = 0;
	public static double gWorldHours = 0;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		LoikvyMod.addNetworkMessage(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
		LoikvyMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
			PlayerVariables clone = new PlayerVariables();
			clone.gPlayerHygene = original.gPlayerHygene;
			clone.gPlayerEndurance = original.gPlayerEndurance;
			clone.gPlayerFitness = original.gPlayerFitness;
			clone.gPlayerStatsPaused = original.gPlayerStatsPaused;
			clone.gPlayerFullName = original.gPlayerFullName;
			clone.gPlayerDeathTime = original.gPlayerDeathTime;
			clone.gPlayerBirthday = original.gPlayerBirthday;
			clone.gPlayerSickness = original.gPlayerSickness;
			clone.last_damage_type = original.last_damage_type;
			clone.gRemoveDoor = original.gRemoveDoor;
			clone.gPlayerLives = original.gPlayerLives;
			clone.gPlayerEnergy = original.gPlayerEnergy;
			clone.gPlayerInDream = original.gPlayerInDream;
			clone.gPlayerAlwaysDream = original.gPlayerAlwaysDream;
			clone.gPlayerPreDreamHealth = original.gPlayerPreDreamHealth;
			clone.gPlayerHappiness = original.gPlayerHappiness;
			clone.gPlayerIsClaustrophobic = original.gPlayerIsClaustrophobic;
			clone.gPlayerIsAgoraphobic = original.gPlayerIsAgoraphobic;
			clone.gPlayerIsScaredOfHeights = original.gPlayerIsScaredOfHeights;
			clone.gPlayerIsTough = original.gPlayerIsTough;
			clone.gPlayerIsProneToInjury = original.gPlayerIsProneToInjury;
			clone.gPlayerIsResistantToSickness = original.gPlayerIsResistantToSickness;
			clone.gPlayerIsProneToSickness = original.gPlayerIsProneToSickness;
			clone.gDamageCancelled = original.gDamageCancelled;
			clone.gPlayerBandaged = original.gPlayerBandaged;
			clone.gPlayerBandageDirty = original.gPlayerBandageDirty;
			clone.gPlayerBandageTime = original.gPlayerBandageTime;
			clone.gPlayerBandageItem = original.gPlayerBandageItem;
			if (!event.isWasDeath()) {
				clone.gPlayerCurrentDoorID = original.gPlayerCurrentDoorID;
				clone.gPlayerCurrentBreakBlock = original.gPlayerCurrentBreakBlock;
			}
			event.getEntity().setData(PLAYER_VARIABLES, clone);
		}

		@SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData mapdata = MapVariables.get(event.getEntity().level());
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (mapdata != null)
					PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(0, mapdata));
				if (worlddata != null)
					PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}

		@SubscribeEvent
		public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (worlddata != null)
					PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final String DATA_NAME = "loikvy_worldvars";

		public static WorldVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			WorldVariables data = new WorldVariables();
			data.read(tag, lookupProvider);
			return data;
		}

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		}

		@Override
		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof ServerLevel level)
				PacketDistributor.sendToPlayersInDimension(level, new SavedDataSyncMessage(1, this));
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(WorldVariables::new, WorldVariables::load), DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final String DATA_NAME = "loikvy_mapvars";
		public double GlobalTicks = 0.0;

		public static MapVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			MapVariables data = new MapVariables();
			data.read(tag, lookupProvider);
			return data;
		}

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			GlobalTicks = nbt.getDouble("GlobalTicks");
		}

		@Override
		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			nbt.putDouble("GlobalTicks", GlobalTicks);
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level && !world.isClientSide())
				PacketDistributor.sendToAllPlayers(new SavedDataSyncMessage(0, this));
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAcc) {
				return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new SavedData.Factory<>(MapVariables::new, MapVariables::load), DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
		public static final Type<SavedDataSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "saved_data_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SavedDataSyncMessage message) -> {
			buffer.writeInt(message.dataType);
			if (message.data != null)
				buffer.writeNbt(message.data.save(new CompoundTag(), buffer.registryAccess()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			int dataType = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			SavedData data = null;
			if (nbt != null) {
				data = dataType == 0 ? new MapVariables() : new WorldVariables();
				if (data instanceof MapVariables mapVariables)
					mapVariables.read(nbt, buffer.registryAccess());
				else if (data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt, buffer.registryAccess());
			}
			return new SavedDataSyncMessage(dataType, data);
		});

		@Override
		public Type<SavedDataSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final SavedDataSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					if (message.dataType == 0)
						MapVariables.clientSide.read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
					else
						WorldVariables.clientSide.read(message.data.save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		public double gPlayerHygene = 100.0;
		public double gPlayerEndurance = 100.0;
		public double gPlayerFitness = 5.0;
		public boolean gPlayerStatsPaused = false;
		public String gPlayerFullName = "\"\"";
		public double gPlayerDeathTime = 0.0;
		public String gPlayerBirthday = "\"1/1/1\"";
		public String gPlayerCurrentDoorID = "\"\"";
		public double gPlayerSickness = 0;
		public String last_damage_type = "\"\"";
		public boolean gRemoveDoor = false;
		public double gPlayerLives = 3.0;
		public double gPlayerEnergy = 100.0;
		public boolean gPlayerInDream = false;
		public boolean gPlayerAlwaysDream = false;
		public double gPlayerPreDreamHealth = 0;
		public double gPlayerHappiness = 100.0;
		public boolean gPlayerIsClaustrophobic = false;
		public boolean gPlayerIsAgoraphobic = false;
		public boolean gPlayerIsScaredOfHeights = false;
		public boolean gPlayerIsTough = false;
		public boolean gPlayerIsProneToInjury = false;
		public boolean gPlayerIsResistantToSickness = false;
		public boolean gPlayerIsProneToSickness = false;
		public boolean gDamageCancelled = false;
		public boolean gPlayerBandaged = false;
		public double gPlayerBandageDirty = 0;
		public double gPlayerBandageTime = 0;
		public ItemStack gPlayerBandageItem = ItemStack.EMPTY;
		public String gPlayerCurrentBreakBlock = "\"\"";

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("gPlayerHygene", gPlayerHygene);
			nbt.putDouble("gPlayerEndurance", gPlayerEndurance);
			nbt.putDouble("gPlayerFitness", gPlayerFitness);
			nbt.putBoolean("gPlayerStatsPaused", gPlayerStatsPaused);
			nbt.putString("gPlayerFullName", gPlayerFullName);
			nbt.putDouble("gPlayerDeathTime", gPlayerDeathTime);
			nbt.putString("gPlayerBirthday", gPlayerBirthday);
			nbt.putString("gPlayerCurrentDoorID", gPlayerCurrentDoorID);
			nbt.putDouble("gPlayerSickness", gPlayerSickness);
			nbt.putString("last_damage_type", last_damage_type);
			nbt.putBoolean("gRemoveDoor", gRemoveDoor);
			nbt.putDouble("gPlayerLives", gPlayerLives);
			nbt.putDouble("gPlayerEnergy", gPlayerEnergy);
			nbt.putBoolean("gPlayerInDream", gPlayerInDream);
			nbt.putBoolean("gPlayerAlwaysDream", gPlayerAlwaysDream);
			nbt.putDouble("gPlayerPreDreamHealth", gPlayerPreDreamHealth);
			nbt.putDouble("gPlayerHappiness", gPlayerHappiness);
			nbt.putBoolean("gPlayerIsClaustrophobic", gPlayerIsClaustrophobic);
			nbt.putBoolean("gPlayerIsAgoraphobic", gPlayerIsAgoraphobic);
			nbt.putBoolean("gPlayerIsScaredOfHeights", gPlayerIsScaredOfHeights);
			nbt.putBoolean("gPlayerIsTough", gPlayerIsTough);
			nbt.putBoolean("gPlayerIsProneToInjury", gPlayerIsProneToInjury);
			nbt.putBoolean("gPlayerIsResistantToSickness", gPlayerIsResistantToSickness);
			nbt.putBoolean("gPlayerIsProneToSickness", gPlayerIsProneToSickness);
			nbt.putBoolean("gDamageCancelled", gDamageCancelled);
			nbt.putBoolean("gPlayerBandaged", gPlayerBandaged);
			nbt.putDouble("gPlayerBandageDirty", gPlayerBandageDirty);
			nbt.putDouble("gPlayerBandageTime", gPlayerBandageTime);
			nbt.put("gPlayerBandageItem", gPlayerBandageItem.saveOptional(lookupProvider));
			nbt.putString("gPlayerCurrentBreakBlock", gPlayerCurrentBreakBlock);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			gPlayerHygene = nbt.getDouble("gPlayerHygene");
			gPlayerEndurance = nbt.getDouble("gPlayerEndurance");
			gPlayerFitness = nbt.getDouble("gPlayerFitness");
			gPlayerStatsPaused = nbt.getBoolean("gPlayerStatsPaused");
			gPlayerFullName = nbt.getString("gPlayerFullName");
			gPlayerDeathTime = nbt.getDouble("gPlayerDeathTime");
			gPlayerBirthday = nbt.getString("gPlayerBirthday");
			gPlayerCurrentDoorID = nbt.getString("gPlayerCurrentDoorID");
			gPlayerSickness = nbt.getDouble("gPlayerSickness");
			last_damage_type = nbt.getString("last_damage_type");
			gRemoveDoor = nbt.getBoolean("gRemoveDoor");
			gPlayerLives = nbt.getDouble("gPlayerLives");
			gPlayerEnergy = nbt.getDouble("gPlayerEnergy");
			gPlayerInDream = nbt.getBoolean("gPlayerInDream");
			gPlayerAlwaysDream = nbt.getBoolean("gPlayerAlwaysDream");
			gPlayerPreDreamHealth = nbt.getDouble("gPlayerPreDreamHealth");
			gPlayerHappiness = nbt.getDouble("gPlayerHappiness");
			gPlayerIsClaustrophobic = nbt.getBoolean("gPlayerIsClaustrophobic");
			gPlayerIsAgoraphobic = nbt.getBoolean("gPlayerIsAgoraphobic");
			gPlayerIsScaredOfHeights = nbt.getBoolean("gPlayerIsScaredOfHeights");
			gPlayerIsTough = nbt.getBoolean("gPlayerIsTough");
			gPlayerIsProneToInjury = nbt.getBoolean("gPlayerIsProneToInjury");
			gPlayerIsResistantToSickness = nbt.getBoolean("gPlayerIsResistantToSickness");
			gPlayerIsProneToSickness = nbt.getBoolean("gPlayerIsProneToSickness");
			gDamageCancelled = nbt.getBoolean("gDamageCancelled");
			gPlayerBandaged = nbt.getBoolean("gPlayerBandaged");
			gPlayerBandageDirty = nbt.getDouble("gPlayerBandageDirty");
			gPlayerBandageTime = nbt.getDouble("gPlayerBandageTime");
			gPlayerBandageItem = ItemStack.parseOptional(lookupProvider, nbt.getCompound("gPlayerBandageItem"));
			gPlayerCurrentBreakBlock = nbt.getString("gPlayerCurrentBreakBlock");
		}

		public void syncPlayerVariables(Entity entity) {
			if (!entity.level().isClientSide()) {
				for (Entity entityiterator : new ArrayList<>(entity.level().players())) {
					if (entityiterator instanceof ServerPlayer serverPlayer)
						PacketDistributor.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this, entity.getId()));
				}
			}
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data, int target) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(LoikvyMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> {
			buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess()));
			buffer.writeInt(message.target()); // Write the entity ID to the buffer
		}, (RegistryFriendlyByteBuf buffer) -> {
			var nbt = buffer.readNbt();
			PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables(), buffer.readInt());
			message.data.deserializeNBT(buffer.registryAccess(), nbt);
			return message;
		});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> context.player().level().getEntity(message.target()).getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess())))
						.exceptionally(e -> {
							context.connection().disconnect(Component.literal(e.getMessage()));
							return null;
						});
			}
		}
	}
}