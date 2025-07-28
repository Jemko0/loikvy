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

import net.mcreator.loikvy.world.inventory.DoorLockIDGUIMenu;
import net.mcreator.loikvy.procedures.GetPlayerDoorLockIDStringProcedure;
import net.mcreator.loikvy.network.DoorLockIDGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class DoorLockIDGUIScreen extends AbstractContainerScreen<DoorLockIDGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	EditBox door_id;
	Button button_set;
	Button button_master_lock;

	public DoorLockIDGUIScreen(DoorLockIDGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 72;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("door_id"))
				door_id.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/door_lock_idgui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		door_id.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (door_id.isFocused())
			return door_id.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String door_idValue = door_id.getValue();
		super.resize(minecraft, width, height);
		door_id.setValue(door_idValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.door_lock_idgui.label_change_door_lock_id"), 5, 5, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.door_lock_idgui.label_current_id"), 5, 19, -12829636, false);
		guiGraphics.drawString(this.font, GetPlayerDoorLockIDStringProcedure.execute(entity), 67, 19, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		door_id = new EditBox(this.font, this.leftPos + 9, this.topPos + 47, 118, 18, Component.translatable("gui.loikvy.door_lock_idgui.door_id"));
		door_id.setMaxLength(8192);
		door_id.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "door_id", content, false);
		});
		door_id.setHint(Component.translatable("gui.loikvy.door_lock_idgui.door_id"));
		this.addWidget(this.door_id);
		button_set = Button.builder(Component.translatable("gui.loikvy.door_lock_idgui.button_set"), e -> {
			int x = DoorLockIDGUIScreen.this.x;
			int y = DoorLockIDGUIScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new DoorLockIDGUIButtonMessage(0, x, y, z));
				DoorLockIDGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 130, this.topPos + 46, 40, 20).build();
		this.addRenderableWidget(button_set);
		button_master_lock = Button.builder(Component.translatable("gui.loikvy.door_lock_idgui.button_master_lock"), e -> {
		}).bounds(this.leftPos + 2, this.topPos + -19, 82, 20).build();
		this.addRenderableWidget(button_master_lock);
	}
}