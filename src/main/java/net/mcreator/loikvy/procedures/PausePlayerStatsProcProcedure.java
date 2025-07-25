package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.loikvy.network.LoikvyModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class PausePlayerStatsProcProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		{
			LoikvyModVariables.PlayerVariables _vars = (commandParameterEntity(arguments, "target")).getData(LoikvyModVariables.PLAYER_VARIABLES);
			_vars.gPlayerStatsPaused = !(commandParameterEntity(arguments, "target")).getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerStatsPaused;
			_vars.syncPlayerVariables((commandParameterEntity(arguments, "target")));
		}
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