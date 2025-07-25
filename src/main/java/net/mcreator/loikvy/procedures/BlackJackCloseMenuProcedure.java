package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

public class BlackJackCloseMenuProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "started") && (entity.getDisplayName().getString()).equals(getBlockNBTString(world, BlockPos.containing(x, y, z), "player"))) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u00A74Bad Sport!"), false);
			BlackjackEndGameProcedure.execute(world, x, y, z, 2);
			GamblingResetPlayerProcedure.execute(world, x, y, z);
		}
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