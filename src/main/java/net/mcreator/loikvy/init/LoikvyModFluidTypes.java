/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.fluids.FluidType;

import net.mcreator.loikvy.fluid.types.GasolineFluidType;
import net.mcreator.loikvy.LoikvyMod;

public class LoikvyModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, LoikvyMod.MODID);
	public static final DeferredHolder<FluidType, FluidType> GASOLINE_TYPE = REGISTRY.register("gasoline", () -> new GasolineFluidType());
}