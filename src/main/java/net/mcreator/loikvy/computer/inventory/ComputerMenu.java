package net.mcreator.loikvy.computer.inventory;

import net.mcreator.loikvy.computer.LoikvyComputerRegistry;
import net.mcreator.loikvy.computer.block.entity.ComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ComputerMenu extends AbstractContainerMenu {

    public final Level world;
    public final Player entity;
    public final ComputerBlockEntity blockEntity;
    public final BlockPos pos;

    // Server-side constructor
    public ComputerMenu(int id, Inventory inv, ComputerBlockEntity blockEntity) {
        super(LoikvyComputerRegistry.COMPUTER_MENU.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.blockEntity = blockEntity;
        this.pos = blockEntity.getBlockPos();

        // Player inventory removed for clean OS UI
    }

    // Client-side constructor
    public ComputerMenu(int id, Inventory inv, RegistryFriendlyByteBuf extraData) {
        this(id, inv, getClientTileEntity(inv, extraData.readBlockPos()));
    }

    private static ComputerBlockEntity getClientTileEntity(Inventory inv, BlockPos pos) {
        if (inv.player.level().getBlockEntity(pos) instanceof ComputerBlockEntity be) {
            return be;
        }
        // Fallback for initialization before chunk loads
        return new ComputerBlockEntity(pos, inv.player.level().getBlockState(pos));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY; // No custom specific items to move right now
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity != null && player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5D,
                blockEntity.getBlockPos().getY() + 0.5D, blockEntity.getBlockPos().getZ() + 0.5D) <= 64.0D;
    }
}
