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

import net.mcreator.loikvy.world.inventory.BookEditGUIMenu;
import net.mcreator.loikvy.network.BookEditGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class BookEditGUIScreen extends AbstractContainerScreen<BookEditGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	EditBox author;
	EditBox title;
	Button button_cancel;
	Button button_apply;

	public BookEditGUIScreen(BookEditGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 300;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("author"))
				author.setValue(stringState);
			else if (name.equals("title"))
				title.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/book_edit_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		author.render(guiGraphics, mouseX, mouseY, partialTicks);
		title.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (author.isFocused())
			return author.keyPressed(key, b, c);
		if (title.isFocused())
			return title.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String authorValue = author.getValue();
		String titleValue = title.getValue();
		super.resize(minecraft, width, height);
		author.setValue(authorValue);
		title.setValue(titleValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.book_edit_gui.label_book"), 5, 5, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		author = new EditBox(this.font, this.leftPos + 31, this.topPos + 18, 118, 18, Component.translatable("gui.loikvy.book_edit_gui.author"));
		author.setMaxLength(8192);
		author.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "author", content, false);
		});
		author.setHint(Component.translatable("gui.loikvy.book_edit_gui.author"));
		this.addWidget(this.author);
		title = new EditBox(this.font, this.leftPos + 31, this.topPos + 41, 118, 18, Component.translatable("gui.loikvy.book_edit_gui.title"));
		title.setMaxLength(8192);
		title.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "title", content, false);
		});
		title.setHint(Component.translatable("gui.loikvy.book_edit_gui.title"));
		this.addWidget(this.title);
		button_cancel = Button.builder(Component.translatable("gui.loikvy.book_edit_gui.button_cancel"), e -> {
			int x = BookEditGUIScreen.this.x;
			int y = BookEditGUIScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new BookEditGUIButtonMessage(0, x, y, z));
				BookEditGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 140, 56, 20).build();
		this.addRenderableWidget(button_cancel);
		button_apply = Button.builder(Component.translatable("gui.loikvy.book_edit_gui.button_apply"), e -> {
		}).bounds(this.leftPos + 239, this.topPos + 140, 51, 20).build();
		this.addRenderableWidget(button_apply);
	}
}