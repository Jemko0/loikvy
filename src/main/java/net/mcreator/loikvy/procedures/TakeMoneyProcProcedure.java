package net.mcreator.loikvy.procedures;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class TakeMoneyProcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments) {
		{
			Entity _ent = (commandParameterEntity(arguments, "target"));
			Scoreboard _sc = _ent.level().getScoreboard();
			Objective _so = _sc.getObjective("Money");
			if (_so == null)
				_so = _sc.addObjective("Money", ObjectiveCriteria.DUMMY, Component.literal("Money"), ObjectiveCriteria.RenderType.INTEGER, true, null);
			_sc.getOrCreatePlayerScore(ScoreHolder.forNameOnly(_ent.getScoreboardName()), _so).set((int) (getEntityScore("Money", (commandParameterEntity(arguments, "target"))) - DoubleArgumentType.getDouble(arguments, "amount")));
		}
		if (!(StringArgumentType.getString(arguments, "name")).equals("0")) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("data modify storage bank_history " + (commandParameterEntity(arguments, "target")).getDisplayName().getString() + "append value {\"date\":\"" + GetCalendarDaysProcedure.execute() + " "
								+ GetClockTimeFormattedProcedure.execute() + "\",\"amount\":" + Math.floor(DoubleArgumentType.getDouble(arguments, "amount")) * (-1) + ",\"name\":\"" + StringArgumentType.getString(arguments, "name") + "\"}"));
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

	private static int getEntityScore(String score, Entity entity) {
		Scoreboard scoreboard = entity.level().getScoreboard();
		Objective scoreboardObjective = scoreboard.getObjective(score);
		if (scoreboardObjective != null)
			return scoreboard.getOrCreatePlayerScore(ScoreHolder.forNameOnly(entity.getScoreboardName()), scoreboardObjective).get();
		return 0;
	}
}