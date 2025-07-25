package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.init.LoikvyModBlocks;

public class FreezerBlockAddedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.setBlock(BlockPos.containing(x, y + 1, z), LoikvyModBlocks.FRIDGE.get().defaultBlockState(), 3);
		{
			Direction _dir = (getBlockDirection(world, BlockPos.containing(x, y, z)));
			BlockPos _pos = BlockPos.containing(x, y + 1, z);
			BlockState _bs = world.getBlockState(_pos);
			Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
			if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
				world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
			} else {
				_property = _bs.getBlock().getStateDefinition().getProperty("axis");
				if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
					world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
			}
		}
	}

	private static Direction getBlockDirection(LevelAccessor world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		Property<?> property = blockState.getBlock().getStateDefinition().getProperty("facing");
		if (property != null && blockState.getValue(property) instanceof Direction direction)
			return direction;
		else if (blockState.hasProperty(BlockStateProperties.AXIS))
			return Direction.fromAxisAndDirection(blockState.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
		else if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
			return Direction.fromAxisAndDirection(blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}
}