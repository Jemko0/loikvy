package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class GetCurrentCapacityProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		return "" + getMaxEnergyStored(world, BlockPos.containing(x, y, z), null);
	}

	public static int getMaxEnergyStored(LevelAccessor level, BlockPos pos, Direction direction) {
		if (level instanceof ILevelExtension levelExtension) {
			IEnergyStorage energyStorage = levelExtension.getCapability(Capabilities.EnergyStorage.BLOCK, pos, direction);
			if (energyStorage != null)
				return energyStorage.getMaxEnergyStored();
		}
		return 0;
	}
}