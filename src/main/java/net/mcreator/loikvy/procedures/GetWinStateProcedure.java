package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GetWinStateProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "win_state") == -1) {
			return "";
		}
		if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "win_state") == 0) {
			return "\u00A72Player Wins";
		}
		if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "win_state") == 1) {
			return "\u00A76Tie Game";
		}
		if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "win_state") == 2) {
			return "\u00A74Dealer Wins";
		}
		return "\u00A74ILLEGAL MOVE ?";
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDouble(tag);
		return -1;
	}
}