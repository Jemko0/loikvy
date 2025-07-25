package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

public class FridgeOnRandomClientDisplayTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof Level _level0 && _level0.hasNeighborSignal(BlockPos.containing(x, y - 1, z))) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x - 0.5, y - 0.5, z - 0.5), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:fridge_hum_short2")), SoundSource.BLOCKS, (float) 0.25, 1);
				} else {
					_level.playLocalSound((x - 0.5), (y - 0.5), (z - 0.5), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:fridge_hum_short2")), SoundSource.BLOCKS, (float) 0.25, 1, false);
				}
			}
		}
	}
}