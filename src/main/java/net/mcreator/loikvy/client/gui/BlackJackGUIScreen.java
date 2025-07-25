package net.mcreator.loikvy.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.loikvy.world.inventory.BlackJackGUIMenu;
import net.mcreator.loikvy.procedures.SlotsGetBetStringProcedure;
import net.mcreator.loikvy.procedures.IsBlackjackStartedProcedure;
import net.mcreator.loikvy.procedures.IsBlackjackNOTStartedProcedure;
import net.mcreator.loikvy.procedures.GetWinStateProcedure;
import net.mcreator.loikvy.procedures.GetSlotsErrorProcedure;
import net.mcreator.loikvy.procedures.GetPlayerCardNumberProcedure;
import net.mcreator.loikvy.procedures.GetDealerCardNumberProcedure;
import net.mcreator.loikvy.procedures.GetBlackJackDebugValuesProcedure;
import net.mcreator.loikvy.network.BlackJackGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class BlackJackGUIScreen extends AbstractContainerScreen<BlackJackGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	Button button_hit;
	Button button_stand;
	Button button_start;
	Button button_2;
	Button button_50;
	Button button_x2;
	Button button_50eu;

	public BlackJackGUIScreen(BlackJackGUIMenu container, Inventory inventory, Component text) {
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

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/black_jack_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.black_jack_gui.label_blackjack"), 65, 6, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.black_jack_gui.label_bet"), 20, 119, -12829636, false);
		guiGraphics.drawString(this.font, SlotsGetBetStringProcedure.execute(world, x, y, z), 42, 119, -16751104, false);
		guiGraphics.drawString(this.font, GetSlotsErrorProcedure.execute(world, x, y, z), 6, 17, -65536, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.black_jack_gui.label_you_have"), 36, 42, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.black_jack_gui.label_the_dealer_has"), 5, 31, -12829636, false);
		guiGraphics.drawString(this.font, GetDealerCardNumberProcedure.execute(world, x, y, z), 88, 31, -13408768, false);
		if (IsBlackjackNOTStartedProcedure.execute(world, x, y, z))
			guiGraphics.drawString(this.font, GetBlackJackDebugValuesProcedure.execute(world, x, y, z), -124, -35, -39424, false);
		guiGraphics.drawString(this.font, GetPlayerCardNumberProcedure.execute(world, x, y, z), 88, 42, -12829636, false);
		guiGraphics.drawString(this.font, GetWinStateProcedure.execute(world, x, y, z), 20, 63, -16724992, false);
	}

	@Override
	public void init() {
		super.init();
		button_hit = Button.builder(Component.translatable("gui.loikvy.black_jack_gui.button_hit"), e -> {
			if (IsBlackjackStartedProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new BlackJackGUIButtonMessage(0, x, y, z));
				BlackJackGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 125, this.topPos + 79, 31, 20).build();
		this.addRenderableWidget(button_hit);
		button_stand = Button.builder(Component.translatable("gui.loikvy.black_jack_gui.button_stand"), e -> {
			if (IsBlackjackStartedProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new BlackJackGUIButtonMessage(1, x, y, z));
				BlackJackGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 20, this.topPos + 79, 31, 20).build();
		this.addRenderableWidget(button_stand);
		button_start = Button.builder(Component.translatable("gui.loikvy.black_jack_gui.button_start"), e -> {
			if (IsBlackjackNOTStartedProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new BlackJackGUIButtonMessage(2, x, y, z));
				BlackJackGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 63, this.topPos + 79, 51, 20).build();
		this.addRenderableWidget(button_start);
		button_2 = Button.builder(Component.translatable("gui.loikvy.black_jack_gui.button_2"), e -> {
			if (IsBlackjackNOTStartedProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new BlackJackGUIButtonMessage(3, x, y, z));
				BlackJackGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 20, this.topPos + 135, 21, 20).build();
		this.addRenderableWidget(button_2);
		button_50 = Button.builder(Component.translatable("gui.loikvy.black_jack_gui.button_50"), e -> {
			if (IsBlackjackNOTStartedProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new BlackJackGUIButtonMessage(4, x, y, z));
				BlackJackGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 42, this.topPos + 135, 21, 20).build();
		this.addRenderableWidget(button_50);
		button_x2 = Button.builder(Component.translatable("gui.loikvy.black_jack_gui.button_x2"), e -> {
			if (IsBlackjackNOTStartedProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new BlackJackGUIButtonMessage(5, x, y, z));
				BlackJackGUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}).bounds(this.leftPos + 135, this.topPos + 135, 21, 20).build();
		this.addRenderableWidget(button_x2);
		button_50eu = Button.builder(Component.translatable("gui.loikvy.black_jack_gui.button_50eu"), e -> {
			if (IsBlackjackNOTStartedProcedure.execute(world, x, y, z)) {
				PacketDistributor.sendToServer(new BlackJackGUIButtonMessage(6, x, y, z));
				BlackJackGUIButtonMessage.handleButtonAction(entity, 6, x, y, z);
			}
		}).bounds(this.leftPos + 113, this.topPos + 135, 21, 20).build();
		this.addRenderableWidget(button_50eu);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_hit.visible = IsBlackjackStartedProcedure.execute(world, x, y, z);
		this.button_stand.visible = IsBlackjackStartedProcedure.execute(world, x, y, z);
		this.button_start.visible = IsBlackjackNOTStartedProcedure.execute(world, x, y, z);
		this.button_2.visible = IsBlackjackNOTStartedProcedure.execute(world, x, y, z);
		this.button_50.visible = IsBlackjackNOTStartedProcedure.execute(world, x, y, z);
		this.button_x2.visible = IsBlackjackNOTStartedProcedure.execute(world, x, y, z);
		this.button_50eu.visible = IsBlackjackNOTStartedProcedure.execute(world, x, y, z);
	}
}