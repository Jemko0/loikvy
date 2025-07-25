package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

public class CookingPanOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean hasHeat = false;
		double i = 0;
		ItemStack item = ItemStack.EMPTY;
		hasHeat = (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock().getStateDefinition().getProperty("lit") instanceof BooleanProperty _getbp1 && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getValue(_getbp1);
		if (!world.isClientSide()) {
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_blockEntity != null)
				_blockEntity.getPersistentData().putBoolean("heat", hasHeat);
			if (world instanceof Level _level)
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
		}
		if (hasHeat) {
			CookingTickProcedure.execute(world, x, y, z, true, 1, 5, 1);
		}
	}
}