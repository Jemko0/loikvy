package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.loikvy.network.LoikvyModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class RegisterPlayerProcProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		double sepid = 0;
		double len = 0;
		String first = "";
		String last = "";
		sepid = (commandParameterEntity(arguments, "player")).getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName.indexOf(" ", 0);
		len = ((commandParameterEntity(arguments, "player")).getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName).length();
		first = (commandParameterEntity(arguments, "player")).getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName.substring(0, (int) sepid);
		last = (commandParameterEntity(arguments, "player")).getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName.substring((int) sepid);
		RegisterPersonProcedure.execute(world, commandParameterEntity(arguments, "player"), first, last);
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return EntityArgument.getEntity(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}