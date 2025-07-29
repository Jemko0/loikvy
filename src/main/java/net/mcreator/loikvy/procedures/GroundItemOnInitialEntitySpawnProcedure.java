package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

public class GroundItemOnInitialEntitySpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("GREETING"), false);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = EntityType.ITEM_DISPLAY.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setXRot(90);
				entityToSpawn.setDeltaMovement(0, 0, 0);
			}
		}
	}
}