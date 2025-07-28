package net.mcreator.loikvy.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.loikvy.world.inventory.StoveGUIMenu;
import net.mcreator.loikvy.procedures.StoveLightSpriteIndexProcedure;
import net.mcreator.loikvy.network.StoveGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class StoveGUIScreen extends AbstractContainerScreen<StoveGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_io;

	public StoveGUIScreen(StoveGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/stove_gui.png");

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
		guiGraphics.blit(ResourceLocation.parse("loikvy:textures/screens/gui_lights_sprite.png"), this.leftPos + 131, this.topPos + 8, Mth.clamp((int) StoveLightSpriteIndexProcedure.execute(world, x, y, z) * 16, 0, 32), 0, 16, 16, 48, 16);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.stove_gui.label_stove"), 6, 5, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.stove_gui.label_inventory"), 7, 72, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_io = Button.builder(Component.translatable("gui.loikvy.stove_gui.button_io"), e -> {
			int x = StoveGUIScreen.this.x;
			int y = StoveGUIScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new StoveGUIButtonMessage(0, x, y, z));
				StoveGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 150, this.topPos + 6, 19, 20).build();
		this.addRenderableWidget(button_io);
	}
}