/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.loikvy.LoikvyMod;

public class LoikvyModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, LoikvyMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> LOIKVY_DEATH = REGISTRY.register("loikvy_death", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "loikvy_death")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LY_DOOR_LOCK = REGISTRY.register("ly_door_lock", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "ly_door_lock")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LY_DOOR_UNLOCK = REGISTRY.register("ly_door_unlock", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "ly_door_unlock")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SMOKE_DETECTOR_CHIRP = REGISTRY.register("smoke_detector_chirp", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "smoke_detector_chirp")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FRIDGE_HUM = REGISTRY.register("fridge_hum", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "fridge_hum")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FRIDGE_HUM_SHORT = REGISTRY.register("fridge_hum_short", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "fridge_hum_short")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FRIDGE_HUM_SHORT2 = REGISTRY.register("fridge_hum_short2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "fridge_hum_short2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DREAM0 = REGISTRY.register("dream0", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "dream0")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DREAM1 = REGISTRY.register("dream1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "dream1")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DREAM2 = REGISTRY.register("dream2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "dream2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DREAM3 = REGISTRY.register("dream3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("loikvy", "dream3")));
}