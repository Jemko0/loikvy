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

import net.mcreator.loikvy.world.inventory.SlotsGUIMenu;
import net.mcreator.loikvy.procedures.SlotsGetBetStringProcedure;
import net.mcreator.loikvy.procedures.RollingIndexSlot2Procedure;
import net.mcreator.loikvy.procedures.RollingIndexSlot1Procedure;
import net.mcreator.loikvy.procedures.RollingIndexProcedure;
import net.mcreator.loikvy.procedures.IsRollingProcedure;
import net.mcreator.loikvy.procedures.GetSlotsErrorProcedure;
import net.mcreator.loikvy.network.SlotsGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class SlotsGUIScreen extends AbstractContainerScreen<SlotsGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_roll;
	Button button_empty;
	Button button_empty1;
	Button button_2;
	Button button_21;

	public SlotsGUIScreen(SlotsGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 174;
		this.imageHeight = 124;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/slots_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		boolean customTooltipShown = false;
		if (mouseX > leftPos + 59 && mouseX < leftPos + 115 && mouseY > topPos + 56 && mouseY < topPos + 76) {
			guiGraphics.renderTooltip(font, Component.translatable("gui.loikvy.slots_gui.tooltip_empty"), mouseX, mouseY);
			customTooltipShown = true;
		}
		if (!customTooltipShown)
			this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(ResourceLocation.parse("loikvy:textures/screens/gambling_slots_8.png"), this.leftPos + 59, this.topPos + 35, Mth.clamp((int) RollingIndexProcedure.execute(world, x, y, z) * 16, 0, 112), 0, 16, 16, 128, 16);
		guiGraphics.blit(ResourceLocation.parse("loikvy:textures/screens/gambling_slots_8.png"), this.leftPos + 79, this.topPos + 35, Mth.clamp((int) RollingIndexSlot1Procedure.execute(world, x, y, z) * 16, 0, 112), 0, 16, 16, 128, 16);
		guiGraphics.blit(ResourceLocation.parse("loikvy:textures/screens/gambling_slots_8.png"), this.leftPos + 99, this.topPos + 35, Mth.clamp((int) RollingIndexSlot2Procedure.execute(world, x, y, z) * 16, 0, 112), 0, 16, 16, 128, 16);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.slots_gui.label_slots"), 75, 5, -13421773, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.slots_gui.label_bet"), 6, 82, -12829636, false);
		guiGraphics.drawString(this.font, SlotsGetBetStringProcedure.execute(world, x, y, z), 25, 81, -16751104, false);
		guiGraphics.drawString(this.font, GetSlotsErrorProcedure.execute(world, x, y, z), 4, 17, -65536, false);
	}

	@Override
	public void init() {
		super.init();
		button_roll = Button.builder(Component.translatable("gui.loikvy.slots_gui.button_roll"), e -> {
			if (IsRollingProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new SlotsGUIButtonMessage(0, x, y, z));
				SlotsGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 59, this.topPos + 56, 56, 20).build();
		this.addRenderableWidget(button_roll);
		button_empty = Button.builder(Component.translatable("gui.loikvy.slots_gui.button_empty"), e -> {
			if (IsRollingProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new SlotsGUIButtonMessage(1, x, y, z));
				SlotsGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 32, this.topPos + 100, 35, 20).build();
		this.addRenderableWidget(button_empty);
		button_empty1 = Button.builder(Component.translatable("gui.loikvy.slots_gui.button_empty1"), e -> {
			if (IsRollingProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new SlotsGUIButtonMessage(2, x, y, z));
				SlotsGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 105, this.topPos + 100, 35, 20).build();
		this.addRenderableWidget(button_empty1);
		button_2 = Button.builder(Component.translatable("gui.loikvy.slots_gui.button_2"), e -> {
			if (IsRollingProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new SlotsGUIButtonMessage(3, x, y, z));
				SlotsGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 141, this.topPos + 100, 20, 20).build();
		this.addRenderableWidget(button_2);
		button_21 = Button.builder(Component.translatable("gui.loikvy.slots_gui.button_21"), e -> {
			if (IsRollingProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new SlotsGUIButtonMessage(4, x, y, z));
				SlotsGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 10, this.topPos + 100, 21, 20).build();
		this.addRenderableWidget(button_21);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_roll.visible = IsRollingProcedure.execute(world, x, y, z);
		this.button_empty.visible = IsRollingProcedure.execute(world, x, y, z);
		this.button_empty1.visible = IsRollingProcedure.execute(world, x, y, z);
		this.button_2.visible = IsRollingProcedure.execute(world, x, y, z);
		this.button_21.visible = IsRollingProcedure.execute(world, x, y, z);
	}
}