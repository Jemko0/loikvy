package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.loikvy.network.LoikvyModVariables;

public class DebugGetPlayerStatString2Procedure {
	public static String execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return "";
		return "Bandage Dirty: " + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty + " / DeathTime: " + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerDeathTime + " / GlobalTicks: "
				+ LoikvyModVariables.MapVariables.get(world).GlobalTicks + " / FullName: " + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerFullName + " / BreakBlock: "
				+ entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock;
	}
}