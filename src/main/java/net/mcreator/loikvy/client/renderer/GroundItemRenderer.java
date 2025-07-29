package net.mcreator.loikvy.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

import net.mcreator.loikvy.entity.GroundItemEntity;

import com.mojang.blaze3d.vertex.PoseStack;

public class GroundItemRenderer extends HumanoidMobRenderer<GroundItemEntity, HumanoidModel<GroundItemEntity>> {
	public GroundItemRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<GroundItemEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	protected void scale(GroundItemEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.01f, 0.01f, 0.01f);
	}

	@Override
	public ResourceLocation getTextureLocation(GroundItemEntity entity) {
		return ResourceLocation.parse("loikvy:textures/entities/player_tired_eyebags.png");
	}

	@Override
	protected boolean isBodyVisible(GroundItemEntity entity) {
		return false;
	}
}