package net.mcreator.loikvy.procedures;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.loikvy.network.LoikvyModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SendMoneyProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		Entity ent = null;
		double targetMoney = 0;
		double execMoney = 0;
		double transferAmt = 0;
		ent = commandParameterEntity(arguments, "target");
		targetMoney = getEntityScore("Money", ent);
		execMoney = getEntityScore("Money", entity);
		transferAmt = DoubleArgumentType.getDouble(arguments, "amount");
		if (HasEnoughMoneyProcedure.execute(entity, transferAmt)) {
			if (!(entity == (commandParameterEntity(arguments, "target")))) {
				{
					Entity _ent = entity;
					Scoreboard _sc = _ent.level().getScoreboard();
					Objective _so = _sc.getObjective("Money");
					if (_so == null)
						_so = _sc.addObjective("Money", ObjectiveCriteria.DUMMY, Component.literal("Money"), ObjectiveCriteria.RenderType.INTEGER, true, null);
					_sc.getOrCreatePlayerScore(ScoreHolder.forNameOnly(_ent.getScoreboardName()), _so).set((int) (execMoney - transferAmt));
				}
				{
					Entity _ent = ent;
					Scoreboard _sc = _ent.level().getScoreboard();
					Objective _so = _sc.getObjective("Money");
					if (_so == null)
						_so = _sc.addObjective("Money", ObjectiveCriteria.DUMMY, Component.literal("Money"), ObjectiveCriteria.RenderType.INTEGER, true, null);
					_sc.getOrCreatePlayerScore(ScoreHolder.forNameOnly(_ent.getScoreboardName()), _so).set((int) (targetMoney + transferAmt));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("\u00A72sent \u00A7a" + new java.text.DecimalFormat("##.## L\u20AC").format(DoubleArgumentType.getDouble(arguments, "amount")) + " \u00A72to \u00A76"
							+ (commandParameterEntity(arguments, "target")).getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName)), false);
				if ((commandParameterEntity(arguments, "target")) instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("\u00A72received \u00A7a" + new java.text.DecimalFormat("##.## L\u20AC").format(DoubleArgumentType.getDouble(arguments, "amount")) + " \u00A72from \u00A76"
							+ entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName)), false);
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("\u00A7cCannot transfer money to yourself"), false);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u00A7cYou do not have enough money to complete this transaction"), false);
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