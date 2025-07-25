package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GetPlayerCardNumberProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		return new java.text.DecimalFormat("##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "player_card"));
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDouble(tag);
		return -1;
	}
}