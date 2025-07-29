package net.mcreator.loikvy.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.loikvy.world.inventory.AppWifiSettingsMenu;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class AppWifiSettingsScreen extends AbstractContainerScreen<AppWifiSettingsMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;

	public AppWifiSettingsScreen(AppWifiSettingsMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 118;
		this.imageHeight = 199;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/app_wifi_settings.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.app_wifi_settings.label_wifi_settings"), 27, 6, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.app_wifi_settings.label_strength"), 6, 26, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.app_wifi_settings.label_ipaddress"), 6, 53, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.app_wifi_settings.label_name"), 6, 79, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
	}
}