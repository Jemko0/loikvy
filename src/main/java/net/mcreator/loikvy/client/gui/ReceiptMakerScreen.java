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
	EditBox itemname;
	EditBox store;
	EditBox color;
	EditBox itemamt;
	EditBox itemprice;
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
			if (name.equals("itemname"))
				itemname.setValue(stringState);
			else if (name.equals("store"))
				store.setValue(stringState);
			else if (name.equals("color"))
				color.setValue(stringState);
			else if (name.equals("itemamt"))
				itemamt.setValue(stringState);
			else if (name.equals("itemprice"))
				itemprice.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/receipt_maker.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		itemname.render(guiGraphics, mouseX, mouseY, partialTicks);
		store.render(guiGraphics, mouseX, mouseY, partialTicks);
		color.render(guiGraphics, mouseX, mouseY, partialTicks);
		itemamt.render(guiGraphics, mouseX, mouseY, partialTicks);
		itemprice.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (itemname.isFocused())
			return itemname.keyPressed(key, b, c);
		if (store.isFocused())
			return store.keyPressed(key, b, c);
		if (color.isFocused())
			return color.keyPressed(key, b, c);
		if (itemamt.isFocused())
			return itemamt.keyPressed(key, b, c);
		if (itemprice.isFocused())
			return itemprice.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String itemnameValue = itemname.getValue();
		String storeValue = store.getValue();
		String colorValue = color.getValue();
		String itemamtValue = itemamt.getValue();
		String itempriceValue = itemprice.getValue();
		super.resize(minecraft, width, height);
		itemname.setValue(itemnameValue);
		store.setValue(storeValue);
		color.setValue(colorValue);
		itemamt.setValue(itemamtValue);
		itemprice.setValue(itempriceValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.receipt_maker.label_receipt_maker"), 5, 5, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.receipt_maker.label_ss"), 5, 47, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		itemname = new EditBox(this.font, this.leftPos + 65, this.topPos + 42, 118, 18, Component.translatable("gui.loikvy.receipt_maker.itemname"));
		itemname.setMaxLength(8192);
		itemname.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "itemname", content, false);
		});
		itemname.setHint(Component.translatable("gui.loikvy.receipt_maker.itemname"));
		this.addWidget(this.itemname);
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
		itemamt = new EditBox(this.font, this.leftPos + 37, this.topPos + 42, 24, 18, Component.translatable("gui.loikvy.receipt_maker.itemamt"));
		itemamt.setMaxLength(8192);
		itemamt.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "itemamt", content, false);
		});
		itemamt.setHint(Component.translatable("gui.loikvy.receipt_maker.itemamt"));
		this.addWidget(this.itemamt);
		itemprice = new EditBox(this.font, this.leftPos + 187, this.topPos + 42, 26, 18, Component.translatable("gui.loikvy.receipt_maker.itemprice"));
		itemprice.setMaxLength(8192);
		itemprice.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "itemprice", content, false);
		});
		itemprice.setHint(Component.translatable("gui.loikvy.receipt_maker.itemprice"));
		this.addWidget(this.itemprice);
		button_make = Button.builder(Component.translatable("gui.loikvy.receipt_maker.button_make"), e -> {
			int x = ReceiptMakerScreen.this.x;
			int y = ReceiptMakerScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new ReceiptMakerButtonMessage(0, x, y, z));
				ReceiptMakerButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 198, this.topPos + 64, 46, 20).build();
		this.addRenderableWidget(button_make);
		button_empty1 = Button.builder(Component.translatable("gui.loikvy.receipt_maker.button_empty1"), e -> {
			int x = ReceiptMakerScreen.this.x;
			int y = ReceiptMakerScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new ReceiptMakerButtonMessage(1, x, y, z));
				ReceiptMakerButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 224, this.topPos + 41, 19, 20).build();
		this.addRenderableWidget(button_empty1);
	}
}