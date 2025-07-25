package net.mcreator.loikvy.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class GamblingSlotsMachineOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double winMultiplier = 0;
		double payout = 0;
		double slotMatches = 0;
		if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "roll")) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("slot0",
							((getBlockNBTNumber(world, BlockPos.containing(x, y, z), "slot0") + Math.floor(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "roll_speed") * getBlockNBTNumber(world, BlockPos.containing(x, y, z), "offset0")))
									% 8));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("slot1",
							((getBlockNBTNumber(world, BlockPos.containing(x, y, z), "slot1") + Math.floor(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "roll_speed") * getBlockNBTNumber(world, BlockPos.containing(x, y, z), "offset1")))
									% 8));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("slot2",
							((getBlockNBTNumber(world, BlockPos.containing(x, y, z), "slot2") + Math.floor(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "roll_speed") * getBlockNBTNumber(world, BlockPos.containing(x, y, z), "offset2")))
									% 8));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("roll_speed", Math.max(0, getBlockNBTNumber(world, BlockPos.containing(x, y, z), "roll_speed") * 0.97));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "roll_speed") < 0.5) {
				if (!world.isClientSide()) {
					BlockPos _bp = BlockPos.containing(x, y, z);
					BlockEntity _blockEntity = world.getBlockEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_blockEntity != null)
						_blockEntity.getPersistentData().putBoolean("roll", false);
					if (world instanceof Level _level)
						_level.sendBlockUpdated(_bp, _bs, _bs, 3);
				}
				winMultiplier = 0;
				slotMatches = SlotsTwoSlotWinProcedure.execute(world, x, y, z);
				if (slotMatches > 0) {
					winMultiplier = 2;
					if (slotMatches > 1) {
						winMultiplier = 3;
					}
				}
				if (winMultiplier > 0) {
					payout = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "bet") * winMultiplier;
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("takemoney " + getBlockNBTString(world, BlockPos.containing(x, y, z), "player") + " " + payout * (-1) + " " + "\"Casino Slots Payout\""));
				}
			}
		} else {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("roll_speed", 8);
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("offset0", (Mth.nextDouble(RandomSource.create(), 0.5, 4)));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("offset1", (Mth.nextDouble(RandomSource.create(), 0.5, 4)));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("offset2", (Mth.nextDouble(RandomSource.create(), 0.5, 4)));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
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

	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getString(tag);
		return "";
	}
}