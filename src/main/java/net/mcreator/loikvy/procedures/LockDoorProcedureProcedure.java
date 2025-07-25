package net.mcreator.loikvy.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class LockDoorProcedureProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, String doorID) {
		if (doorID == null)
			return false;
		if (IsDoorLockedProcedure.execute(world, x, y, z)) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("data modify storage minecraft:locked_doors" + " locked[{\"door\":{\"pos\":\"" + (x + "_" + y + "_" + z) + "\",\"id\":\"" + doorID + "\"}}].door.locked set value " + "0b"));
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("data modify storage minecraft:locked_doors" + " locked[{\"door\":{\"pos\":\"" + (x + "_" + (y + 1) + "_" + z) + "\",\"id\":\"" + doorID + "\"}}].door.locked set value " + "0b"));
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("data modify storage minecraft:locked_doors" + " locked[{\"door\":{\"pos\":\"" + (x + "_" + (y - 1) + "_" + z) + "\",\"id\":\"" + doorID + "\"}}].door.locked set value " + "0b"));
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:ly_door_unlock")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:ly_door_unlock")), SoundSource.BLOCKS, 1, 1, false);
					}
				}
			}
			return false;
		}
		if (world instanceof ServerLevel _level)
			_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
					("data modify storage minecraft:locked_doors" + " locked[{\"door\":{\"pos\":\"" + (x + "_" + y + "_" + z) + "\",\"id\":\"" + doorID + "\"}}].door.locked set value " + "1b"));
		if (world instanceof ServerLevel _level)
			_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
					("data modify storage minecraft:locked_doors" + " locked[{\"door\":{\"pos\":\"" + (x + "_" + (y + 1) + "_" + z) + "\",\"id\":\"" + doorID + "\"}}].door.locked set value " + "1b"));
		if (world instanceof ServerLevel _level)
			_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
					("data modify storage minecraft:locked_doors" + " locked[{\"door\":{\"pos\":\"" + (x + "_" + (y - 1) + "_" + z) + "\",\"id\":\"" + doorID + "\"}}].door.locked set value " + "1b"));
		if (!world.isClientSide()) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:ly_door_lock")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("loikvy:ly_door_lock")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
		}
		return true;
	}
}