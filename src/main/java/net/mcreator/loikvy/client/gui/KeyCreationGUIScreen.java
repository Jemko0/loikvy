package net.mcreator.loikvy.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mcreator.loikvy.world.inventory.KeyCreationGUIMenu;
import net.mcreator.loikvy.network.KeyCreationGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class KeyCreationGUIScreen extends AbstractContainerScreen<KeyCreationGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	EditBox key_id;
	Button button_create;

	public KeyCreationGUIScreen(KeyCreationGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 135;
		this.imageHeight = 72;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("key_id"))
				key_id.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/key_creation_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		key_id.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
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
		if (key_id.isFocused())
			return key_id.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String key_idValue = key_id.getValue();
		super.resize(minecraft, width, height);
		key_id.setValue(key_idValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.key_creation_gui.label_create_key"), 43, 4, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		key_id = new EditBox(this.font, this.leftPos + 8, this.topPos + 19, 118, 18, Component.translatable("gui.loikvy.key_creation_gui.key_id"));
		key_id.setMaxLength(8192);
		key_id.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "key_id", content, false);
		});
		key_id.setHint(Component.translatable("gui.loikvy.key_creation_gui.key_id"));
		this.addWidget(this.key_id);
		button_create = Button.builder(Component.translatable("gui.loikvy.key_creation_gui.button_create"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new KeyCreationGUIButtonMessage(0, x, y, z));
				KeyCreationGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 72, this.topPos + 45, 56, 20).build();
		this.addRenderableWidget(button_create);
	}
}