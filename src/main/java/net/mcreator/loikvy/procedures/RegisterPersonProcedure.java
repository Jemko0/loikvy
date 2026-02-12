package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class RegisterPersonProcedure {
	public static void execute(LevelAccessor world, Entity entity, String firstName, String lastName) {
		if (entity == null || firstName == null || lastName == null)
			return;
		String sanitizedName = "";
		String sanitizedBirth = "";
		{
			LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerRegisterName = firstName + "" + lastName;
			_vars.syncPlayerVariables(entity);
		}
		sanitizedName = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName.replace(" ", "_");
		sanitizedBirth = entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBirthday.replace(".", "/");
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList()
					.broadcastSystemMessage(Component.literal((executeCommandGetResult(entity,
							("data modify storage minecraft:registry citizens." + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerRegisterName + " set value " + "{" + MakeJSONPropertyProcedure.execute(false, true, firstName, "first_name")
									+ MakeJSONPropertyProcedure.execute(false, true, lastName, "last_name") + MakeJSONPropertyProcedure.execute(false, true, sanitizedBirth, "DoB") + MakeJSONPropertyProcedure.execute(false, true, "1b", "alive")
									+ MakeJSONPropertyProcedure.execute(false, true, "0/0/0", "date_of_death") + MakeJSONPropertyProcedure.execute(false, true, "none", "occupation")
									+ MakeJSONPropertyProcedure.execute(false, true, "none", "main_residency") + MakeJSONPropertyProcedure.execute(true, false, "", "criminal_record") + "}")))),
							false);
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