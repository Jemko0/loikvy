package net.mcreator.loikvy.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

import net.mcreator.loikvy.entity.ShopCashierEntity;

public class ShopCashierRenderer extends HumanoidMobRenderer<ShopCashierEntity, HumanoidModel<ShopCashierEntity>> {
	public ShopCashierRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<ShopCashierEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(ShopCashierEntity entity) {
		return ResourceLocation.parse("loikvy:textures/entities/cashier.png");
	}
}