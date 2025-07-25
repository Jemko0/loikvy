package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

public class SmokeDetectorOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		ItemStack item = ItemStack.EMPTY;
		double amt = 0;
		item = (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).copy();
		amt = itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).getCount();
		if (item.is(ItemTags.create(ResourceLocation.parse("loikvy:energy_source"))) && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "powered")) {
			if (item.getDamageValue() < 999) {
				if (world instanceof ServerLevel _level) {
					item.hurtAndBreak(1, _level, null, _stkprov -> {
					});
				}
				if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
					ItemStack _setstack = item.copy();
					_setstack.setCount((int) amt);
					_itemHandlerModifiable.setStackInSlot(0, _setstack);
				}
				if (item.getDamageValue() >= 985) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:smoke_detector_chirp")), SoundSource.BLOCKS, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:smoke_detector_chirp")), SoundSource.BLOCKS, 3, 1, false);
						}
					}
				}
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

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBoolean(tag);
		return false;
	}
}