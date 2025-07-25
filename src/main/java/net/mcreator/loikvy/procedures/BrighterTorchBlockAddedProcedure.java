package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class BrighterTorchBlockAddedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double extraLightSize = 0;
		double xIter = 0;
		double zIter = 0;
		xIter = 0;
		zIter = 0;
		extraLightSize = 5;
		while (xIter < extraLightSize) {
			while (zIter < extraLightSize) {
				if ((world.getBlockState(BlockPos.containing((xIter + x) - extraLightSize / 2, y, (zIter + z) - extraLightSize / 2))).getBlock() == Blocks.AIR) {
					world.setBlock(BlockPos.containing((xIter + x) - extraLightSize / 2, y, (zIter + z) - extraLightSize / 2), Blocks.LIGHT.defaultBlockState(), 3);
					{
						int _value = 15;
						BlockPos _pos = BlockPos.containing((xIter + x) - extraLightSize / 2, y, (zIter + z) - extraLightSize / 2);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("level") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				}
				zIter = zIter + 1;
			}
			zIter = 0;
			xIter = xIter + 1;
		}
	}
}