package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public class StoveLightSpriteIndexProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		double idx = 0;
		idx = 0;
		if (world instanceof Level _level0 && _level0.hasNeighborSignal(BlockPos.containing(x, y, z))) {
			idx = 1;
			if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "lit")) {
				idx = 2;
			}
		}
		return idx;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBoolean(tag);
		return false;
	}
}