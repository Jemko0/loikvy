package net.mcreator.loikvy.computer.os;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Global registry for ComputerApps.
 * This determines what apps exist in the game and can be instantiated on a
 * ComputerBlockEntity screen.
 */
public class AppRegistry {

    // Map of app IDs to a Supplier that creates a new instance of the app (to keep
    // UI state isolated per screen instance).
    private static final Map<ResourceLocation, Supplier<AbstractApp>> REGISTERED_APPS = new HashMap<>();

    public static void register(ResourceLocation id, Supplier<AbstractApp> factory) {
        REGISTERED_APPS.put(id, factory);
    }

    public static AbstractApp createAppInstance(ResourceLocation id) {
        Supplier<AbstractApp> factory = REGISTERED_APPS.get(id);
        if (factory != null) {
            return factory.get();
        }
        return null; // App doesn't exist or isn't installed
    }

    public static Map<ResourceLocation, Supplier<AbstractApp>> getRegisteredApps() {
        return REGISTERED_APPS;
    }
}
