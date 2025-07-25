/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import net.mcreator.loikvy.fluid.GasolineFluid;
import net.mcreator.loikvy.LoikvyMod;

public class LoikvyModFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, LoikvyMod.MODID);
	public static final DeferredHolder<Fluid, FlowingFluid> GASOLINE = REGISTRY.register("gasoline", () -> new GasolineFluid.Source());
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_GASOLINE = REGISTRY.register("flowing_gasoline", () -> new GasolineFluid.Flowing());

	@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class FluidsClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(GASOLINE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_GASOLINE.get(), RenderType.translucent());
		}
	}
}