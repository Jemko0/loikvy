package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class CoalGeneratorOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean c = false;
		c = !((itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem() == Blocks.AIR.asItem()) && (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getBurnTime(null) > 0;
		if (c) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("fuel", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "fuel") + (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getBurnTime(null)));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
				int _slotid = 0;
				ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
				_stk.shrink(1);
				_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
			}
		}
		if (!world.isClientSide()) {
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_blockEntity != null)
				_blockEntity.getPersistentData().putBoolean("powered", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "fuel") > 0));
			if (world instanceof Level _level)
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
		}
		if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "powered")) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("fuel", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "fuel") - 1));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (world instanceof ILevelExtension _ext) {
				IEnergyStorage _entityStorage = _ext.getCapability(Capabilities.EnergyStorage.BLOCK, BlockPos.containing(x, y, z), null);
				if (_entityStorage != null)
					_entityStorage.receiveEnergy(extractEnergySimulate(world, BlockPos.containing(x, y, z), 240, null), false);
			}
		}
		if (world.isClientSide()) {
			if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "powered")) {
				world.addParticle(ParticleTypes.ASH, x, y, z, 0, 2, 0);
			}
		}
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
			if (itemHandler != null)
				return itemHandler.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
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

	private static int extractEnergySimulate(LevelAccessor level, BlockPos pos, int amount, Direction direction) {
		if (level instanceof ILevelExtension levelExtension) {
			IEnergyStorage energyStorage = levelExtension.getCapability(Capabilities.EnergyStorage.BLOCK, pos, direction);
			if (energyStorage != null)
				return energyStorage.extractEnergy(amount, true);
		}
		return 0;
	}
}