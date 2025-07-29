/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.loikvy.client.renderer.WheelchairRenderer;
import net.mcreator.loikvy.client.renderer.ShopCashierRenderer;
import net.mcreator.loikvy.client.renderer.GroundItemRenderer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LoikvyModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(LoikvyModEntities.SHOP_CASHIER.get(), ShopCashierRenderer::new);
		event.registerEntityRenderer(LoikvyModEntities.WHEELCHAIR.get(), WheelchairRenderer::new);
		event.registerEntityRenderer(LoikvyModEntities.GROUND_ITEM.get(), GroundItemRenderer::new);
	}
}