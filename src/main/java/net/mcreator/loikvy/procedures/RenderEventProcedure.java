package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.Minecraft;

import net.mcreator.loikvy.network.LoikvyModVariables;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

@EventBusSubscriber(value = {Dist.CLIENT})
public class RenderEventProcedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onEventTriggered(RenderLivingEvent.Pre event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		RenderLivingEvent _evt = (RenderLivingEvent) event;
		Minecraft mc = Minecraft.getInstance();
		EntityRenderDispatcher dis = Minecraft.getInstance().getEntityRenderDispatcher();
		EntityRendererProvider.Context context = new EntityRendererProvider.Context(dis, mc.getItemRenderer(), mc.getBlockRenderer(), dis.getItemInHandRenderer(), mc.getResourceManager(), mc.getEntityModels(), mc.font);
		Entity _evtEntity = _evt.getEntity();
		PlayerRenderer _pr = null;
		PoseStack poseStack = _evt.getPoseStack();
		if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
			ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/empty.png");
			com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer emptyRenderer = new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context,
					(_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture);
			_pr = emptyRenderer;
			emptyRenderer.clearLayers();
			emptyRenderer.render((AbstractClientPlayer) _evtEntity, _evtEntity.getYRot(), _evt.getPartialTick(), poseStack, _evt.getMultiBufferSource(), _evt.getPackedLight());
		}
		if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.25) {
			if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_damage_high.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_damage_high.png");
				}
				new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
						.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
			}
		} else if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.5) {
			if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_damaged_medium.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_damaged_medium.png");
				}
				new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
						.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
			}
		} else if ((double) (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.75) {
			if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_damage_low.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_damage_low.png");
				}
				new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
						.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
			}
		}
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene <= 25) {
			if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_dirty_extreme.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_dirty_extreme.png");
				}
				new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
						.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
			}
		} else if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene <= 50) {
			if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_dirty_high.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_dirty_high.png");
				}
				new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
						.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
			}
		} else if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHygene <= 75) {
			if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_dirty_low.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_dirty_low.png");
				}
				new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
						.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
			}
		}
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerEnergy <= 50) {
			if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
				ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
				if (ResourceLocation.tryParse("loikvy:textures/entities/player_tired_eyebags.png") != null) {
					_texture = ResourceLocation.parse("loikvy:textures/entities/player_tired_eyebags.png");
				}
				new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
						.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
			}
		}
		if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandaged) {
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty <= 5) {
				if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
					ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
					if (ResourceLocation.tryParse("loikvy:textures/entities/player_self_bandaged.png") != null) {
						_texture = ResourceLocation.parse("loikvy:textures/entities/player_self_bandaged.png");
					}
					new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
							.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
				}
			} else if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty <= 45) {
				if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
					ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
					if (ResourceLocation.tryParse("loikvy:textures/entities/player_self_bandaged_dirty1.png") != null) {
						_texture = ResourceLocation.parse("loikvy:textures/entities/player_self_bandaged_dirty1.png");
					}
					new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
							.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
				}
			} else if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerBandageDirty <= 70) {
				if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
					ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
					if (ResourceLocation.tryParse("loikvy:textures/entities/player_self_bandaged_dirty2.png") != null) {
						_texture = ResourceLocation.parse("loikvy:textures/entities/player_self_bandaged_dirty2.png");
					}
					new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
							.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
				}
			} else {
				if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof com.kleiders.kleidersplayerrenderer.KleidersIgnoreCancel)) {
					ResourceLocation _texture = ResourceLocation.parse("kleiders_custom_renderer:textures/entities/default.png");
					if (ResourceLocation.tryParse("loikvy:textures/entities/player_self_bandaged_dirty3.png") != null) {
						_texture = ResourceLocation.parse("loikvy:textures/entities/player_self_bandaged_dirty3.png");
					}
					new com.kleiders.kleidersplayerrenderer.KleidersSkinRenderer(context, (_evtEntity instanceof AbstractClientPlayer ? ((AbstractClientPlayer) _evtEntity).getSkin().model() == PlayerSkin.Model.SLIM : false), _texture)
							.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
				}
			}
		}
	}
}