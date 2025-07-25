package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

public class BlackjackStandProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!world.isClientSide()) {
			if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "started")) {
				if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_hidden") > 0) {
					AddCardToDealerProcedure.execute(world, x, y, z, getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_hidden"));
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null)
							_blockEntity.getPersistentData().putDouble("dealer_hidden", 0);
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
				while (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_card") < 17) {
					AddCardToDealerProcedure.execute(world, x, y, z, BlackjackDrawCardProcedure.execute());
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.experience_orb.pickup")), SoundSource.NEUTRAL, (float) 0.9, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.experience_orb.pickup")), SoundSource.NEUTRAL, (float) 0.9, 1, false);
					}
				}
				if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_card") > 21) {
					BlackjackEndGameProcedure.execute(world, x, y, z, 0);
				} else if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "player_card") > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_card")) {
					BlackjackEndGameProcedure.execute(world, x, y, z, 0);
				} else if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_card") > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "player_card")) {
					BlackjackEndGameProcedure.execute(world, x, y, z, 2);
				} else {
					BlackjackEndGameProcedure.execute(world, x, y, z, 1);
				}
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