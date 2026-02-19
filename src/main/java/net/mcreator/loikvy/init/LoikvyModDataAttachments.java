package net.mcreator.loikvy.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LoikvyModDataAttachments {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "loikvy");

    public static final Supplier<DataComponentType<Long>> CREATION_TIME =
            DATA_COMPONENTS.register("creation_time", () ->
                    DataComponentType.<Long>builder()
                            .persistent(Codec.LONG)
                            .build()
            );

    public static void register(IEventBus modBus) {
        DATA_COMPONENTS.register(modBus);
    }
}