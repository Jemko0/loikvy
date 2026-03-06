package net.mcreator.loikvy.utility.networking.interfaces;

import net.minecraft.nbt.CompoundTag;

public interface IStorageReceiver {
    void receiveStorageData(CompoundTag data);
}