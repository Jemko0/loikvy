package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMenus;

public class CharCreatorOnCreatePressedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (CanRespawnProcedure.execute(world, entity)) {
			if (entity instanceof ServerPlayer _player)
				_player.setGameMode(GameType.SURVIVAL);
			{
				Entity _ent = entity;
				_ent.teleportTo((world.getLevelData().getSpawnPos().getX()), (world.getLevelData().getSpawnPos().getY()), (world.getLevelData().getSpawnPos().getZ()));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((world.getLevelData().getSpawnPos().getX()), (world.getLevelData().getSpawnPos().getY()), (world.getLevelData().getSpawnPos().getZ()), _ent.getYRot(), _ent.getXRot());
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerFullName = ((entity instanceof Player _entity5 && _entity5.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu5) ? _menu5.getMenuState(0, "ply_first_name", "") : "") + " "
						+ ((entity instanceof Player _entity6 && _entity6.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu6) ? _menu6.getMenuState(0, "ply_last_name", "") : "");
				_vars.syncPlayerVariables(entity);
			}
			ResetPlayerStatProcProcedure.execute(entity, entity);
			if (entity instanceof LivingEntity _entity)
				_entity.removeAllEffects();
			if (entity instanceof ServerPlayer _serverPlayer)
				_serverPlayer.setRespawnPosition(_serverPlayer.level().dimension(), new BlockPos(world.getLevelData().getSpawnPos().getX(), world.getLevelData().getSpawnPos().getY(), world.getLevelData().getSpawnPos().getZ()),
						_serverPlayer.getYRot(), true, false);
			if (entity instanceof Player _player)
				_player.getInventory().clearContent();
			if (entity instanceof Player _player)
				_player.closeContainer();
			DetermineTraitsProcedure.execute(world, entity);
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerBirthday = GetCalendarDaysProcedure.execute();
				_vars.syncPlayerVariables(entity);
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent),
							("/tellraw @s {\"text\":\"Welcome, " + "" + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName + "!\",\"color\":\"green\"}"));
				}
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/stopsound @s * loikvy:loikvy_death");
				}
			}
		}
	}
}