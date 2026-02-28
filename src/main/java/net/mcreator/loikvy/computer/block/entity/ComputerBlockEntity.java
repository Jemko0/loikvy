package net.mcreator.loikvy.computer.block.entity;

import net.mcreator.loikvy.computer.LoikvyComputerRegistry;
import net.mcreator.loikvy.computer.inventory.ComputerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ComputerBlockEntity extends BlockEntity implements MenuProvider {

    // The OS data storing everything from installed apps to their internal states
    // (like prices)
    private CompoundTag osData = new CompoundTag();

    public ComputerBlockEntity(BlockPos pos, BlockState state) {
        super(LoikvyComputerRegistry.COMPUTER_BLOCK_ENTITY.get(), pos, state);
    }

    public CompoundTag getOsData() {
        return osData;
    }

    public void setOsData(CompoundTag osData) {
        this.osData = osData;
        setChanged(); // Mark chunk as dirty to save
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("OSData", osData);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("OSData")) {
            this.osData = tag.getCompound("OSData");
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Computer OS");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new ComputerMenu(id, inv, this);
    }
}
