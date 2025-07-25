package net.mcreator.loikvy.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelwheelchair<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("loikvy", "modelwheelchair"), "main");
	public final ModelPart bone;

	public Modelwheelchair(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bone = partdefinition.addOrReplaceChild("bone",
				CubeListBuilder.create().texOffs(32, 0).addBox(1.0F, -3.0F, 2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 32).addBox(1.0F, -3.0F, -7.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(5.0F, -6.0F, -6.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 10).addBox(12.0F, -16.0F, -6.0F, 1.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 28)
						.addBox(6.0F, -10.0F, 1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(6.0F, -10.0F, -6.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 4)
						.addBox(5.0F, -10.0F, -6.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(8, 32).addBox(5.0F, -10.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 14)
						.addBox(7.0F, -7.0F, -7.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 22).addBox(7.0F, -7.0F, 2.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-6.0F, 24.0F, 2.0F));
		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(18, 12).addBox(0.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 10).addBox(0.0F, -0.5F, -7.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, -1.5F, 1.5F, 0.0F, 0.0F, -0.1745F));
		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(28, 30).addBox(0.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 30).addBox(0.0F, -0.5F, 6.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, -1.5F, -5.5F, 0.0F, 0.0F, -0.7418F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}