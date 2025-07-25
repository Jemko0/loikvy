package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

public class SinkBlockOnBlockRightClickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if (blockstate.getBlock().getStateDefinition().getProperty("contains_water") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BUCKET) {
				if (entity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(Items.BUCKET);
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(Items.WATER_BUCKET).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("contains_water") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
			} else {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("contains_water") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, (!(blockstate.getBlock().getStateDefinition().getProperty("contains_water") instanceof BooleanProperty _getbp8 && blockstate.getValue(_getbp8)))), 3);
				}
			}
		} else {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("contains_water") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, (!(blockstate.getBlock().getStateDefinition().getProperty("contains_water") instanceof BooleanProperty _getbp11 && blockstate.getValue(_getbp11)))), 3);
			}
		}
	}
}