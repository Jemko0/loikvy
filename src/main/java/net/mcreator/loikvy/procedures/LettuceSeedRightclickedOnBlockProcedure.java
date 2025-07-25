package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.init.LoikvyModItems;
import net.mcreator.loikvy.init.LoikvyModBlocks;

public class LettuceSeedRightclickedOnBlockProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if (Blocks.WHEAT.defaultBlockState().canSurvive(world, BlockPos.containing(x, y + 1, z))) {
			world.setBlock(BlockPos.containing(x, y + 1, z), LoikvyModBlocks.LETTUCE_CROP.get().defaultBlockState(), 3);
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(LoikvyModItems.LETTUCE_SEED.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.CONSUME;
	}
}