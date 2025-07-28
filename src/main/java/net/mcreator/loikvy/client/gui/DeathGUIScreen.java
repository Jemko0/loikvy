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

import net.mcreator.loikvy.world.inventory.DeathGUIMenu;
import net.mcreator.loikvy.procedures.GetLivesStringProcedure;
import net.mcreator.loikvy.procedures.GetCalendarDaysProcedure;
import net.mcreator.loikvy.procedures.CharCreatorGetRespawnTimeStringProcedure;
import net.mcreator.loikvy.procedures.CanNOTRespawnProcedure;
import net.mcreator.loikvy.network.DeathGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class DeathGUIScreen extends AbstractContainerScreen<DeathGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	EditBox ply_first_name;
	EditBox ply_last_name;
	Button button_create;

	public DeathGUIScreen(DeathGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 244;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("ply_first_name"))
				ply_first_name.setValue(stringState);
			else if (name.equals("ply_last_name"))
				ply_last_name.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/death_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		ply_first_name.render(guiGraphics, mouseX, mouseY, partialTicks);
		ply_last_name.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (ply_first_name.isFocused())
			return ply_first_name.keyPressed(key, b, c);
		if (ply_last_name.isFocused())
			return ply_last_name.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String ply_first_nameValue = ply_first_name.getValue();
		String ply_last_nameValue = ply_last_name.getValue();
		super.resize(minecraft, width, height);
		ply_first_name.setValue(ply_first_nameValue);
		ply_last_name.setValue(ply_last_nameValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.death_gui.label_character_editor"), 83, 5, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.death_gui.label_starting_age_18"), 5, 66, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.death_gui.label_birthday"), 5, 78, -12829636, false);
		guiGraphics.drawString(this.font, GetCalendarDaysProcedure.execute(), 54, 78, -12829636, false);
		if (CanNOTRespawnProcedure.execute(world, entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.death_gui.label_you_can_respawn_in"), 6, 144, -65536, false);
		if (CanNOTRespawnProcedure.execute(world, entity))
			guiGraphics.drawString(this.font, CharCreatorGetRespawnTimeStringProcedure.execute(world, entity), 107, 144, -39322, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.death_gui.label_you_have"), 6, 126, -12829636, false);
		guiGraphics.drawString(this.font, GetLivesStringProcedure.execute(entity), 51, 126, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.death_gui.label_lives_left"), 59, 126, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		ply_first_name = new EditBox(this.font, this.leftPos + 6, this.topPos + 20, 118, 18, Component.translatable("gui.loikvy.death_gui.ply_first_name"));
		ply_first_name.setMaxLength(8192);
		ply_first_name.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "ply_first_name", content, false);
		});
		ply_first_name.setHint(Component.translatable("gui.loikvy.death_gui.ply_first_name"));
		this.addWidget(this.ply_first_name);
		ply_last_name = new EditBox(this.font, this.leftPos + 6, this.topPos + 43, 118, 18, Component.translatable("gui.loikvy.death_gui.ply_last_name"));
		ply_last_name.setMaxLength(8192);
		ply_last_name.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "ply_last_name", content, false);
		});
		ply_last_name.setHint(Component.translatable("gui.loikvy.death_gui.ply_last_name"));
		this.addWidget(this.ply_last_name);
		button_create = Button.builder(Component.translatable("gui.loikvy.death_gui.button_create"), e -> {
			int x = DeathGUIScreen.this.x;
			int y = DeathGUIScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new DeathGUIButtonMessage(0, x, y, z));
				DeathGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 183, this.topPos + 141, 56, 20).build();
		this.addRenderableWidget(button_create);
	}
}