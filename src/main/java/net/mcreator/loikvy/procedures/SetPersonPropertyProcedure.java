package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class SetPersonPropertyProcedure {
	public static void execute(LevelAccessor world, Entity entity, String property, String value) {
		if (entity == null || property == null || value == null)
			return;
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(
					Component.literal((executeCommandGetResult(entity, ("data modify storage minecraft:registry citizens." + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerRegisterName + "." + property + " set value " + value)))), false);
	}

	private static String executeCommandGetResult(Entity entity, String command) {
		StringBuilder result = new StringBuilder();
		if (!entity.level().isClientSide() && entity.getServer() != null) {
			CommandSource dataConsumer = new CommandSource() {
				@Override
				public void sendSystemMessage(Component message) {
					result.append(message.getString());
				}

				@Override
				public boolean acceptsSuccess() {
					return true;
				}

				@Override
				public boolean acceptsFailure() {
					return true;
				}

				@Override
				public boolean shouldInformAdmins() {
					return false;
				}
			};
			entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(dataConsumer, entity.position(), entity.getRotationVector(), entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null, 4,
					entity.getName().getString(), entity.getDisplayName(), entity.level().getServer(), entity), command);
		}
		return result.toString();
	}
}