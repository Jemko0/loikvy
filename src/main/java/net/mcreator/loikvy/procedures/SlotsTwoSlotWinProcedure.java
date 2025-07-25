package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class SlotsTwoSlotWinProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		double s1 = 0;
		double s2 = 0;
		double s0 = 0;
		double matches = 0;
		s0 = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "slot0");
		s1 = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "slot1");
		s2 = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "slot2");
		if (s0 == s1) {
			matches = matches + 1;
		}
		if (s0 == s2) {
			matches = matches + 1;
		}
		if (s1 == s2) {
			matches = matches + 1;
		}
		return matches;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDouble(tag);
		return -1;
	}
}