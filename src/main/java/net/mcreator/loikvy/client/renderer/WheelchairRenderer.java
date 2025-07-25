package net.mcreator.loikvy.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.loikvy.entity.WheelchairEntity;
import net.mcreator.loikvy.client.model.Modelwheelchair;

public class WheelchairRenderer extends MobRenderer<WheelchairEntity, Modelwheelchair<WheelchairEntity>> {
	public WheelchairRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwheelchair<WheelchairEntity>(context.bakeLayer(Modelwheelchair.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(WheelchairEntity entity) {
		return ResourceLocation.parse("loikvy:textures/entities/wheelchair_texture.png");
	}
}