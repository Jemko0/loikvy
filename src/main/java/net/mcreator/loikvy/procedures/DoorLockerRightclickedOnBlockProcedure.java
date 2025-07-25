package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.world.inventory.DoorLockIDGUIMenu;
import net.mcreator.loikvy.network.LoikvyModVariables;

import io.netty.buffer.Unpooled;

public class DoorLockerRightclickedOnBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (IsDoorProcedure.execute(world, x, y, z)) {
			if (LockDoorProcedureProcedure.execute(world, x, y, z, entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentDoorID)) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("\u00A7eDoor is now locked."), false);
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("\u00A7eDoor is now unlocked."), false);
			}
		} else {
			if (entity instanceof ServerPlayer _ent) {
				BlockPos _bpos = BlockPos.containing(x, y, z);
				_ent.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("DoorLockIDGUI");
					}

					@Override
					public boolean shouldTriggerClientSideContainerClosingOnOpen() {
						return false;
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new DoorLockIDGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
		}
	}
}