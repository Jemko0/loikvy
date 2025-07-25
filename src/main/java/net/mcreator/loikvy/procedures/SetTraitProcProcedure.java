package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.loikvy.network.LoikvyModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class SetTraitProcProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		boolean state = false;
		Entity ent = null;
		String trait = "";
		state = BoolArgumentType.getBool(arguments, "newState");
		ent = commandParameterEntity(arguments, "target");
		trait = StringArgumentType.getString(arguments, "trait");
		if ((trait).equals("claustrophobic")) {
			{
				LoikvyModVariables.PlayerVariables _vars = ent.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsClaustrophobic = state;
				_vars.syncPlayerVariables(ent);
			}
		}
		if ((trait).equals("agoraphobic")) {
			{
				LoikvyModVariables.PlayerVariables _vars = ent.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsAgoraphobic = state;
				_vars.syncPlayerVariables(ent);
			}
		}
		if ((trait).equals("scared_of_heights")) {
			{
				LoikvyModVariables.PlayerVariables _vars = ent.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsScaredOfHeights = state;
				_vars.syncPlayerVariables(ent);
			}
		}
		if ((trait).equals("tough")) {
			{
				LoikvyModVariables.PlayerVariables _vars = ent.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsTough = state;
				_vars.syncPlayerVariables(ent);
			}
		}
		if ((trait).equals("prone_to_injury")) {
			{
				LoikvyModVariables.PlayerVariables _vars = ent.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToInjury = state;
				_vars.syncPlayerVariables(ent);
			}
		}
		if ((trait).equals("resistant_to_sickness")) {
			{
				LoikvyModVariables.PlayerVariables _vars = ent.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsResistantToSickness = state;
				_vars.syncPlayerVariables(ent);
			}
		}
		if ((trait).equals("prone_to_sickness")) {
			{
				LoikvyModVariables.PlayerVariables _vars = ent.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToSickness = state;
				_vars.syncPlayerVariables(ent);
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