package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class FluidContainerOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		BlockState block = Blocks.AIR.defaultBlockState();
		block = (world.getBlockState(
				new BlockPos((getBlockDirection(world, BlockPos.containing(x, y, z))).getStepX(), (getBlockDirection(world, BlockPos.containing(x, y, z))).getStepY(), (getBlockDirection(world, BlockPos.containing(x, y, z))).getStepZ())));
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal((("x " + (getBlockDirection(world, BlockPos.containing(x, y, z))).getStepX() + " // ") + ""
					+ ("y " + (getBlockDirection(world, BlockPos.containing(x, y, z))).getStepY() + " // ") + ("z " + (getBlockDirection(world, BlockPos.containing(x, y, z))).getStepZ() + " // "))), false);
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