package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

public class RegisterStoreCommandProcProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		RegisterStoreProcedure.execute(world, StringArgumentType.getString(arguments, "additionalInfo"), GetCalendarDaysProcedure.execute(), StringArgumentType.getString(arguments, "ownerName"),
				StringArgumentType.getString(arguments, "responsibleParty"), StringArgumentType.getString(arguments, "storeAddress"), StringArgumentType.getString(arguments, "storeDesiredName"),
				StringArgumentType.getString(arguments, "storeRegistryName"), StringArgumentType.getString(arguments, "storeType"), "" + commandParameterBlockPos(arguments, "storePosition").getX(),
				"" + commandParameterBlockPos(arguments, "storePosition").getY(), "" + commandParameterBlockPos(arguments, "storePosition").getZ());
	}

	private static BlockPos commandParameterBlockPos(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return BlockPosArgument.getLoadedBlockPos(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return new BlockPos(0, 0, 0);
		}
	}
}