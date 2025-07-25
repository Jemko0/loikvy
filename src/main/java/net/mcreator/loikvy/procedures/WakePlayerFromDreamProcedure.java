package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;

public class WakePlayerFromDreamProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double i = 0;
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(LoikvyModMobEffects.SLEEPING);
		PlayerWakesUpProcedure.execute(entity);
		{
			Entity _ent = entity;
			_ent.teleportTo(
					((entity instanceof ServerPlayer _player && !_player.level().isClientSide())
							? ((_player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null) ? _player.getRespawnPosition().getX() : _player.level().getLevelData().getSpawnPos().getX())
							: 0),
					((entity instanceof ServerPlayer _player && !_player.level().isClientSide())
							? ((_player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null) ? _player.getRespawnPosition().getY() : _player.level().getLevelData().getSpawnPos().getY())
							: 0),
					((entity instanceof ServerPlayer _player && !_player.level().isClientSide())
							? ((_player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null) ? _player.getRespawnPosition().getZ() : _player.level().getLevelData().getSpawnPos().getZ())
							: 0));
			if (_ent instanceof ServerPlayer _serverPlayer)
				_serverPlayer.connection.teleport(
						((entity instanceof ServerPlayer _player && !_player.level().isClientSide())
								? ((_player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null) ? _player.getRespawnPosition().getX() : _player.level().getLevelData().getSpawnPos().getX())
								: 0),
						((entity instanceof ServerPlayer _player && !_player.level().isClientSide())
								? ((_player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null) ? _player.getRespawnPosition().getY() : _player.level().getLevelData().getSpawnPos().getY())
								: 0),
						((entity instanceof ServerPlayer _player && !_player.level().isClientSide())
								? ((_player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null) ? _player.getRespawnPosition().getZ() : _player.level().getLevelData().getSpawnPos().getZ())
								: 0),
						_ent.getYRot(), _ent.getXRot());
		}
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth((float) entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerPreDreamHealth);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 300, 0));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.WET, 300, 1));
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerInDream = false;
			_vars.syncPlayerVariables(entity);
		}
		i = 0;
		while (i < 3) {
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/stopsound @s * loikvy:dream0");
				}
			}
			i = i + 1;
		}
	}
}