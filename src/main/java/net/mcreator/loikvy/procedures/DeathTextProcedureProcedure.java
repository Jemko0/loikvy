package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class DeathTextProcedureProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			Entity _ent = entity;
			if (!_ent.level().isClientSide() && _ent.getServer() != null) {
				_ent.getServer().getCommands()
						.performPrefixedCommand(
								new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
										_ent.level().getServer(), _ent),
								("title @s subtitle " + "{\"text\":\"" + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBirthday + " - " + GetCalendarDaysProcedure.execute() + "\",\"color\":\"yellow\"}"));
			}
		}
		{
			Entity _ent = entity;
			if (!_ent.level().isClientSide() && _ent.getServer() != null) {
				_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
						_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), ("title @s title " + "{\"text\":\"" + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName + "\",\"color\":\"red\"}"));
			}
		}
	}
}