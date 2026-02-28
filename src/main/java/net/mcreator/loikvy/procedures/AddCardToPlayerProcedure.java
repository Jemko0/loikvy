package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public class AddCardToPlayerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double card) {
		BlockPos pos = BlockPos.containing(x, y, z);

		double currentTotal = getBlockNBTNumber(world, pos, "player_card");
		double currentAces = getBlockNBTNumber(world, pos, "player_aces");

		if (currentTotal < 0) currentTotal = 0;
		if (currentAces < 0) currentAces = 0;

		if (card == 1) {
			currentAces += 1;
			currentTotal += 11;

			if (!world.isClientSide())
			{
				BlockPos _bp = pos;
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("player_aces", currentAces);

				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		}
		else
		{
			currentTotal += card;
		}

		//Update total
		if (!world.isClientSide()) {
			BlockPos _bp = pos;
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_blockEntity != null)
				_blockEntity.getPersistentData().putDouble("player_card", currentTotal);
			if (world instanceof Level _level)
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
		}

		while (currentTotal > 21 && currentAces > 0) {
			currentTotal -= 10;
			currentAces -= 1;

			if (!world.isClientSide())
			{
				BlockPos _bp = pos;
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
				{
					_blockEntity.getPersistentData().putDouble("player_card", currentTotal);
					_blockEntity.getPersistentData().putDouble("player_aces", currentAces);
				}

				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		}
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDouble(tag);
		return -1;
	}
}