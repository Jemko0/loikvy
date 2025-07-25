package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.loikvy.network.LoikvyModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SetPlayerStatProcProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		Entity target = null;
		String stat = "";
		double val = 0;
		target = commandParameterEntity(arguments, "target");
		stat = StringArgumentType.getString(arguments, "stat");
		val = DoubleArgumentType.getDouble(arguments, "value");
		if ((stat).equals("hygene")) {
			{
				LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerHygene = val;
				_vars.syncPlayerVariables(target);
			}
		}
		if ((stat).equals("endurance")) {
			{
				LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerEndurance = val;
				_vars.syncPlayerVariables(target);
			}
		}
		if ((stat).equals("fitness")) {
			{
				LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerFitness = val;
				_vars.syncPlayerVariables(target);
			}
		}
		if ((stat).equals("sickness")) {
			{
				LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerSickness = val;
				_vars.syncPlayerVariables(target);
			}
		}
		if ((stat).equals("lives")) {
			{
				LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerLives = val;
				_vars.syncPlayerVariables(target);
			}
		}
		if ((stat).equals("happiness")) {
			{
				LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerHappiness = val;
				_vars.syncPlayerVariables(target);
			}
		}
		if ((stat).equals("energy")) {
			{
				LoikvyModVariables.PlayerVariables _vars = target.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerEnergy = val;
				_vars.syncPlayerVariables(target);
			}
		}
		if ((stat).equals("alwaysdream")) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerAlwaysDream = val > 0;
				_vars.syncPlayerVariables(entity);
			}
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