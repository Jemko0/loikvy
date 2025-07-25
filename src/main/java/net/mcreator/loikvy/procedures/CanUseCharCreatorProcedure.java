package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class CanUseCharCreatorProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return getEntityGameType(entity) == GameType.SPECTATOR || (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName).equals("");
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}
}