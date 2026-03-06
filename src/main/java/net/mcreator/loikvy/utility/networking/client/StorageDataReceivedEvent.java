package net.mcreator.loikvy.utility.networking.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;

public class StorageDataReceivedEvent extends Event {
    private final ResourceLocation storageId;
    private final CompoundTag data;

    public StorageDataReceivedEvent(ResourceLocation storageId, CompoundTag data) {
        this.storageId = storageId;
        this.data = data;
    }

    public ResourceLocation getStorageId() {
        return storageId;
    }

    public CompoundTag getData() {
        return data;
    }
}