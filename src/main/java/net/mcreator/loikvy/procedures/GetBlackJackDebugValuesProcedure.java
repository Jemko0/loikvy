package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GetBlackJackDebugValuesProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		return "dealer_card: " + new java.text.DecimalFormat("##.##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_card")) + " / dealer_hidden: "
				+ new java.text.DecimalFormat("##.##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_hidden")) + " / dealer_aces: "
				+ new java.text.DecimalFormat("##.##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "dealer_aces")) + "/ player_card: "
				+ new java.text.DecimalFormat("##.##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "player_card")) + "/ player_aces: "
				+ new java.text.DecimalFormat("##.##").format(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "player_aces")) + "/ started: " + ("" + getBlockNBTLogic(world, BlockPos.containing(x, y, z), "started")) + "/ player: "
				+ getBlockNBTString(world, BlockPos.containing(x, y, z), "player");
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDouble(tag);
		return -1;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBoolean(tag);
		return false;
	}

	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getString(tag);
		return "";
	}
}