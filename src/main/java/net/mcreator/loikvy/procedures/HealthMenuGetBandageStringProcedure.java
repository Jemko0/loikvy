package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;

public class HealthMenuGetBandageStringProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		double currentHealth = 0;
		double maxHealth = 0;
		String StatusString = "";
		currentHealth = entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1;
		maxHealth = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandaged) {
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty > 50) {
				return "\u00A76Dirty Bandage";
			}
			return "\u00A72Bandaged";
		}
		StatusString = "\u00A72OK";
		if (currentHealth < maxHealth) {
			StatusString = "\u00A72Slight Damage";
		}
		if (currentHealth < maxHealth * 0.75) {
			StatusString = "\u00A76Minor Damage";
		}
		if (currentHealth < maxHealth * 0.5) {
			StatusString = "\u00A76Severe Damage";
		}
		if (currentHealth < maxHealth * 0.25) {
			StatusString = "\u00A74Critical Damage";
		}
		if (currentHealth < maxHealth * 0.1) {
			StatusString = "\u00A74Terminal Damage";
		}
		if (currentHealth <= 0 || getEntityGameType(entity) == GameType.SPECTATOR) {
			StatusString = "\u00A74Deceased";
		}
		if (entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(LoikvyModMobEffects.BLEEDING)) {
			StatusString = "\u00A74Bleeding";
		}
		return StatusString;
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