package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class PurchaseProcedure {
	public static void execute(Entity entity, ItemStack item, double amt, double price) {
		if (entity == null)
			return;
		if (HasEnoughMoneyProcedure.execute(entity, price)) {
			{
				Entity _ent = entity;
				Scoreboard _sc = _ent.level().getScoreboard();
				Objective _so = _sc.getObjective("Currency");
				if (_so == null)
					_so = _sc.addObjective("Currency", ObjectiveCriteria.DUMMY, Component.literal("Currency"), ObjectiveCriteria.RenderType.INTEGER, true, null);
				_sc.getOrCreatePlayerScore(ScoreHolder.forNameOnly(_ent.getScoreboardName()), _so).set((int) (getEntityScore("Currency", entity) - price));
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = item.copy();
				_setstack.setCount((int) amt);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u00A72Item purchased successfully."), false);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u00A74You do not have enough Currency to purchase this item."), false);
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