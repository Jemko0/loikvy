package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.init.LoikvyModBlocks;

public class FridgeNeighbourBlockChangesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == LoikvyModBlocks.FREEZER.get())) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		}
	}
}