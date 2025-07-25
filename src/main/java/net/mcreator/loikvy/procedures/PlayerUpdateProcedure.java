package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;
import net.mcreator.loikvy.init.LoikvyModItems;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerUpdateProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double enduranceLoss = 0;
		double ickyLevel = 0;
		double hygeneLoss = 0;
		double sicknessincrease = 0;
		double energyLoss = 0;
		double shortEffectLength = 0;
		double sicknessMultiplier = 0;
		ItemStack bandage = ItemStack.EMPTY;
		sicknessMultiplier = 1;
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsResistantToSickness) {
			sicknessMultiplier = 0.5;
		}
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsProneToSickness) {
			sicknessMultiplier = 2;
		}
		shortEffectLength = 30;
		if (getEntityGameType(entity) == GameType.SPECTATOR) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.DEAD, 2147483646, 0));
		} else {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(LoikvyModMobEffects.DEAD);
		}
		if (!entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerStatsPaused && !(getEntityGameType(entity) == GameType.SPECTATOR)) {
			hygeneLoss = 0.001;
			if (entity.isSprinting()) {
				hygeneLoss = 0.004;
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerHygene = ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene - hygeneLoss);
				_vars.syncPlayerVariables(entity);
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene < 50) {
				ickyLevel = ((80 - entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene) / 100) * 3;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.ICKY, (int) shortEffectLength, (int) ickyLevel));
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene < 15) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.STINKING, (int) shortEffectLength, 0));
			}
			if (entity.isInWater()) {
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent),
								("setplayerstat @s hygene " + ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene + 0.1)));
					}
				}
			}
			enduranceLoss = (-0.01) / GetFitnessMultiplierProcedure.execute(entity);
			if (entity.isSprinting()) {
				enduranceLoss = 0.03 * GetFitnessMultiplierProcedure.execute(entity);
				{
					LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
					_vars.gPlayerFitness = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFitness * 1.000001;
					_vars.syncPlayerVariables(entity);
				}
			}
			if (entity instanceof LivingEntity _livEnt10 && _livEnt10.isSleeping()) {
				enduranceLoss = -0.25;
			}
			if (entity.isShiftKeyDown() && Math.abs(entity.getDeltaMovement().x()) <= 0 && Math.abs(entity.getDeltaMovement().z()) <= 0) {
				enduranceLoss = (-0.03) / GetFitnessMultiplierProcedure.execute(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerEndurance = ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEndurance - enduranceLoss);
				_vars.syncPlayerVariables(entity);
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEndurance <= 50) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.EXERTION, (int) shortEffectLength, 0));
				if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEndurance <= 25) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.EXERTION, (int) shortEffectLength, 1));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.WET, 6000, 0));
					if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEndurance <= 10) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.EXERTION, (int) shortEffectLength, 2));
					}
				}
			}
			sicknessincrease = (-0.002) / sicknessMultiplier;
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (entity.isInWaterRainOrBubble() && !entity.isInWaterOrBubble()) {
					sicknessincrease = 0.006 * sicknessMultiplier;
					if (entity instanceof LivingEntity _livEnt22 && _livEnt22.hasEffect(LoikvyModMobEffects.WET)) {
						sicknessincrease = 0.007 * sicknessMultiplier;
					}
				}
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerSickness = ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerSickness + sicknessincrease);
				_vars.syncPlayerVariables(entity);
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerSickness > 50) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.SICK, (int) shortEffectLength, 0));
			}
			energyLoss = 0.00138;
			if (entity.isShiftKeyDown()) {
				energyLoss = -0.0008;
			}
			if (entity.isSprinting()) {
				energyLoss = 0.00177;
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerEnergy = ClampNumberProcedure.execute(100, 0, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEnergy - energyLoss);
				_vars.syncPlayerVariables(entity);
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEnergy < 15) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.TIRED, (int) shortEffectLength, 0));
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEnergy < 7.5) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.TIRED, (int) shortEffectLength, 1));
			}
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHappiness < 50) {
				if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHappiness < 10) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.DEPRESSED, (int) shortEffectLength, 0));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.SAD, (int) shortEffectLength, 0));
				}
			}
			if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) < 0.5) {
				if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) < 0.25) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.MAJOR_INJURY, (int) shortEffectLength, 0));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.MINOR_INJURY, (int) shortEffectLength, 0));
				}
			}
			if (entity instanceof LivingEntity _livEnt36 && _livEnt36.hasEffect(LoikvyModMobEffects.PANIC)) {
				PlayerGainHappinessProcedure.execute(entity, -0.003);
			}
			if (entity instanceof LivingEntity _livEnt37 && _livEnt37.hasEffect(LoikvyModMobEffects.BLEEDING)) {
				PlayerGainHappinessProcedure.execute(entity, -0.007);
			}
			if (entity instanceof LivingEntity _livEnt38 && _livEnt38.hasEffect(LoikvyModMobEffects.DRUNK)) {
				PlayerGainHappinessProcedure.execute(entity, 0.0004);
			}
			if (entity instanceof LivingEntity _livEnt39 && _livEnt39.hasEffect(LoikvyModMobEffects.OVERSTIMULATED)) {
				PlayerGainHappinessProcedure.execute(entity, -0.002);
			}
			if (entity instanceof LivingEntity _livEnt40 && _livEnt40.hasEffect(LoikvyModMobEffects.OVERDOSING)) {
				PlayerGainHappinessProcedure.execute(entity, -0.05);
			}
			if (entity instanceof LivingEntity _livEnt41 && _livEnt41.hasEffect(LoikvyModMobEffects.BLEEDING)) {
				if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandaged) {
					{
						LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
						_vars.gPlayerBandageDirty = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty + 0.01666;
						_vars.syncPlayerVariables(entity);
					}
					if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty > 100) {
						{
							LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
							_vars.gPlayerBandaged = false;
							_vars.syncPlayerVariables(entity);
						}
						{
							LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
							_vars.gPlayerBandageDirty = 0;
							_vars.syncPlayerVariables(entity);
						}
						bandage = new ItemStack(LoikvyModItems.RIPPED_SHEET.get()).copy();
						{
							final String _tagName = "dirty";
							final double _tagValue = 100;
							CustomData.update(DataComponents.CUSTOM_DATA, bandage, tag -> tag.putDouble(_tagName, _tagValue));
						}
						if (entity instanceof Player _player) {
							ItemStack _setstack = bandage.copy();
							_setstack.setCount(1);
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
					}
				} else {
					{
						LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
						_vars.gPlayerBandageDirty = 0;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.LOIKVIAN, 5, 0));
		}
		if (entity instanceof LivingEntity _livEnt45 && _livEnt45.hasEffect(LoikvyModMobEffects.EXERTION) || entity instanceof LivingEntity _livEnt46 && _livEnt46.hasEffect(LoikvyModMobEffects.DEPRESSED)) {
			if (entity.isSprinting()) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.RESTRICTED_MOVEMENT, (int) shortEffectLength, 0));
			}
		}
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}
}