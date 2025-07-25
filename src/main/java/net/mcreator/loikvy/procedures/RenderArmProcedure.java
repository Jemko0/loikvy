package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.Minecraft;

import net.mcreator.loikvy.network.LoikvyModVariables;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

@EventBusSubscriber(value = {Dist.CLIENT})
public class RenderArmProcedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onEventTriggered(RenderArmEvent event) {
		execute(event, event.getPlayer());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		RenderArmEvent _evt = (RenderArmEvent) event;
		Minecraft mc = Minecraft.getInstance();
		EntityRenderDispatcher dis = mc.getEntityRenderDispatcher();
		Entity _evtEntity = _evt.getPlayer();
		PlayerRenderer playerRenderer = (PlayerRenderer) dis.getRenderer(_evt.getPlayer());
		EntityRendererProvider.Context context = new EntityRendererProvider.Context(dis, mc.getItemRenderer(), mc.getBlockRenderer(), dis.getItemInHandRenderer(), mc.getResourceManager(), mc.getEntityModels(), mc.font);
		MultiBufferSource bufferSource = _evt.getMultiBufferSource();
		int packedLight = _evt.getPackedLight();
		PoseStack poseStack = _evt.getPoseStack();
		PlayerModel<AbstractClientPlayer> playerModel = new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false);
		playerModel.attackTime = 0.0F;
		playerModel.crouching = false;
		playerModel.swimAmount = 0.0F;
		playerModel.setupAnim(_evt.getPlayer(), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		playerModel.leftArm.xRot = 0.0F;
		playerModel.rightArm.xRot = 0.0F;
		HumanoidArm arm = _evt.getArm();
		if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.25) {
			{
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_damage_high.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_damage_high.png");
				}
				PlayerModel<AbstractClientPlayer> newModel = new PlayerModel<>(
						context.bakeLayer((_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false) ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
						(_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false));
				newModel.leftArm.copyFrom(playerModel.leftArm);
				newModel.rightArm.copyFrom(playerModel.rightArm);
				if (arm == HumanoidArm.LEFT) {
					newModel.leftArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				} else {
					newModel.rightArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				}
			}
		} else if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.5) {
			{
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_damaged_medium.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_damaged_medium.png");
				}
				PlayerModel<AbstractClientPlayer> newModel = new PlayerModel<>(
						context.bakeLayer((_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false) ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
						(_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false));
				newModel.leftArm.copyFrom(playerModel.leftArm);
				newModel.rightArm.copyFrom(playerModel.rightArm);
				if (arm == HumanoidArm.LEFT) {
					newModel.leftArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				} else {
					newModel.rightArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				}
			}
		} else if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.75) {
			{
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_damage_low.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_damage_low.png");
				}
				PlayerModel<AbstractClientPlayer> newModel = new PlayerModel<>(
						context.bakeLayer((_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false) ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
						(_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false));
				newModel.leftArm.copyFrom(playerModel.leftArm);
				newModel.rightArm.copyFrom(playerModel.rightArm);
				if (arm == HumanoidArm.LEFT) {
					newModel.leftArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				} else {
					newModel.rightArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				}
			}
		}
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene <= 25) {
			{
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_dirty_extreme.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_dirty_extreme.png");
				}
				PlayerModel<AbstractClientPlayer> newModel = new PlayerModel<>(
						context.bakeLayer((_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false) ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
						(_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false));
				newModel.leftArm.copyFrom(playerModel.leftArm);
				newModel.rightArm.copyFrom(playerModel.rightArm);
				if (arm == HumanoidArm.LEFT) {
					newModel.leftArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				} else {
					newModel.rightArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				}
			}
		} else if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene <= 50) {
			{
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_dirty_high.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_dirty_high.png");
				}
				PlayerModel<AbstractClientPlayer> newModel = new PlayerModel<>(
						context.bakeLayer((_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false) ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
						(_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false));
				newModel.leftArm.copyFrom(playerModel.leftArm);
				newModel.rightArm.copyFrom(playerModel.rightArm);
				if (arm == HumanoidArm.LEFT) {
					newModel.leftArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				} else {
					newModel.rightArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				}
			}
		} else if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene <= 75) {
			{
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_dirty_low.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_dirty_low.png");
				}
				PlayerModel<AbstractClientPlayer> newModel = new PlayerModel<>(
						context.bakeLayer((_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false) ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
						(_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false));
				newModel.leftArm.copyFrom(playerModel.leftArm);
				newModel.rightArm.copyFrom(playerModel.rightArm);
				if (arm == HumanoidArm.LEFT) {
					newModel.leftArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				} else {
					newModel.rightArm.render(_evt.getPoseStack(), bufferSource.getBuffer(RenderType.entityTranslucentCull(_texture)), packedLight, OverlayTexture.NO_OVERLAY);
				}
			}
		}
	}
}