package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;
import net.mcreator.loikvy.LoikvyMod;

public class ProcessDreamProcedure {
	public static void execute(LevelAccessor world, Entity entity, double dreamID) {
		if (entity == null)
			return;
		double dreamLength = 0;
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerEnergy = 50;
			_vars.syncPlayerVariables(entity);
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.SLEEPING, 6000, 0, false, false));
		dreamLength = Mth.nextInt(RandomSource.create(), 200, 1200);
		entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), 1);
		entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), -1);
		if (dreamID == 0) {
			{
				Entity _ent = entity;
				_ent.teleportTo(0, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 0, 0)), 0);
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(0, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 0, 0)), 0, _ent.getYRot(), _ent.getXRot());
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound loikvy:dream0 ambient @s ~ ~ ~ 1 1");
				}
			}
		}
		if (dreamID == 1) {
			{
				Entity _ent = entity;
				_ent.teleportTo(1000, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 1000, 0)), 0);
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(1000, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 1000, 0)), 0, _ent.getYRot(), _ent.getXRot());
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound loikvy:dream1 ambient @s ~ ~ ~ 1 1");
				}
			}
		}
		if (dreamID == 2) {
			{
				Entity _ent = entity;
				_ent.teleportTo(2000, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 2000, 0)), 0);
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(2000, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 2000, 0)), 0, _ent.getYRot(), _ent.getXRot());
			}
		}
		if (dreamID == 3) {
			{
				Entity _ent = entity;
				_ent.teleportTo(3000, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 2000, 0)), 0);
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(3000, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 2000, 0)), 0, _ent.getYRot(), _ent.getXRot());
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound loikvy:dream3 ambient @s ~ ~ ~ 1 1");
				}
			}
		}
		LoikvyMod.queueServerWork((int) dreamLength, () -> {
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerInDream) {
				WakePlayerFromDreamProcedure.execute(entity);
			}
		});
	}
}