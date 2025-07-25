package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public class BlackjackHitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double card = 0;
		if (!world.isClientSide()) {
			if (!getBlockNBTLogic(world, BlockPos.containing(x, y, z), "started")) {
				if (!world.isClientSide()) {
					BlockPos _bp = BlockPos.containing(x, y, z);
					BlockEntity _blockEntity = world.getBlockEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_blockEntity != null)
						_blockEntity.getPersistentData().putString("error", "game has not started!");
					if (world instanceof Level _level)
						_level.sendBlockUpdated(_bp, _bs, _bs, 3);
				}
				return;
			}
			card = BlackjackDrawCardProcedure.execute();
			AddCardToPlayerProcedure.execute(world, x, y, z, card);
			if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "player_card") > 21) {
				BlackjackEndGameProcedure.execute(world, x, y, z, 2);
			}
		}
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBoolean(tag);
		return false;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDouble(tag);
		return -1;
	}
}