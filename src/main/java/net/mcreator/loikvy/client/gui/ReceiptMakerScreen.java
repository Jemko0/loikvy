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

import net.mcreator.loikvy.world.inventory.ReceiptMakerMenu;
import net.mcreator.loikvy.network.ReceiptMakerButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class ReceiptMakerScreen extends AbstractContainerScreen<ReceiptMakerMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	EditBox content;
	EditBox store;
	EditBox color;
	Button button_make;
	Button button_empty1;

	public ReceiptMakerScreen(ReceiptMakerMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 250;
		this.imageHeight = 90;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("content"))
				content.setValue(stringState);
			else if (name.equals("store"))
				store.setValue(stringState);
			else if (name.equals("color"))
				color.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/receipt_maker.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		content.render(guiGraphics, mouseX, mouseY, partialTicks);
		store.render(guiGraphics, mouseX, mouseY, partialTicks);
		color.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (content.isFocused())
			return content.keyPressed(key, b, c);
		if (store.isFocused())
			return store.keyPressed(key, b, c);
		if (color.isFocused())
			return color.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String contentValue = content.getValue();
		String storeValue = store.getValue();
		String colorValue = color.getValue();
		super.resize(minecraft, width, height);
		content.setValue(contentValue);
		store.setValue(storeValue);
		color.setValue(colorValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.receipt_maker.label_receipt_maker"), 5, 5, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.receipt_maker.label_ss"), 5, 45, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		content = new EditBox(this.font, this.leftPos + 37, this.topPos + 42, 118, 18, Component.translatable("gui.loikvy.receipt_maker.content"));
		content.setMaxLength(8192);
		content.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "content", content, false);
		});
		content.setHint(Component.translatable("gui.loikvy.receipt_maker.content"));
		this.addWidget(this.content);
		store = new EditBox(this.font, this.leftPos + 12, this.topPos + 18, 118, 18, Component.translatable("gui.loikvy.receipt_maker.store"));
		store.setMaxLength(8192);
		store.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "store", content, false);
		});
		store.setHint(Component.translatable("gui.loikvy.receipt_maker.store"));
		this.addWidget(this.store);
		color = new EditBox(this.font, this.leftPos + 12, this.topPos + 42, 21, 18, Component.translatable("gui.loikvy.receipt_maker.color"));
		color.setMaxLength(8192);
		color.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "color", content, false);
		});
		color.setHint(Component.translatable("gui.loikvy.receipt_maker.color"));
		this.addWidget(this.color);
		button_make = Button.builder(Component.translatable("gui.loikvy.receipt_maker.button_make"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ReceiptMakerButtonMessage(0, x, y, z));
				ReceiptMakerButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 198, this.topPos + 64, 46, 20).build();
		this.addRenderableWidget(button_make);
		button_empty1 = Button.builder(Component.translatable("gui.loikvy.receipt_maker.button_empty1"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ReceiptMakerButtonMessage(1, x, y, z));
				ReceiptMakerButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 158, this.topPos + 41, 19, 20).build();
		this.addRenderableWidget(button_empty1);
	}
}