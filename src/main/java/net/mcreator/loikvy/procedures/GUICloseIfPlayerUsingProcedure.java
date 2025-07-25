package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.LoikvyMod;

public class GUICloseIfPlayerUsingProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		if (!(getBlockNBTString(world, BlockPos.containing(x, y, z), "player")).equals("")) {
			if (entity.hasPermissions(3)) {
				LoikvyMod.LOGGER.info("GUI Closing Bypassed");
				return false;
			} else {
				if (entity instanceof Player _player)
					_player.closeContainer();
				return true;
			}
		}
		return false;
	}

	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getString(tag);
		return "";
	}
}