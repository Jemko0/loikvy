package net.mcreator.loikvy.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.loikvy.world.inventory.HealthMenuGUIMenu;
import net.mcreator.loikvy.procedures.HealthMenuGetBandageStringProcedure;
import net.mcreator.loikvy.procedures.HasBandageProcedure;
import net.mcreator.loikvy.procedures.GetPlayerFullNameStringProcedure;
import net.mcreator.loikvy.procedures.DisplayPlayerModelInDeathProcedure;
import net.mcreator.loikvy.network.HealthMenuGUIButtonMessage;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class HealthMenuGUIScreen extends AbstractContainerScreen<HealthMenuGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	ImageButton imagebutton_button_take_bandage2;

	public HealthMenuGUIScreen(HealthMenuGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 172;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/health_menu_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		if (DisplayPlayerModelInDeathProcedure.execute(entity) instanceof LivingEntity livingEntity) {
			LoikvyModScreens.renderEntityInInventoryFollowsAngle(guiGraphics, this.leftPos + 36, this.topPos + 69, 20, 0f + (float) Math.atan((this.leftPos + 36 - mouseX) / 40.0), (float) Math.atan((this.topPos + 20 - mouseY) / 40.0), livingEntity);
		}
		boolean customTooltipShown = false;
		if (mouseX > leftPos + 54 && mouseX < leftPos + 70 && mouseY > topPos + 36 && mouseY < topPos + 52) {
			guiGraphics.renderTooltip(font, Component.translatable("gui.loikvy.health_menu_gui.tooltip_take_off"), mouseX, mouseY);
			customTooltipShown = true;
		}
		if (!customTooltipShown)
			this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(ResourceLocation.parse("loikvy:textures/screens/health_menu_entity_32.png"), this.leftPos + 20, this.topPos + 25, 0, 0, 32, 48, 32, 48);
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
		guiGraphics.drawString(this.font, GetPlayerFullNameStringProcedure.execute(entity), 5, 5, -12829636, false);
		guiGraphics.drawString(this.font, HealthMenuGetBandageStringProcedure.execute(entity), 54, 24, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_button_take_bandage2 = new ImageButton(this.leftPos + 54, this.topPos + 36, 16, 16,
				new WidgetSprites(ResourceLocation.parse("loikvy:textures/screens/button_take_bandage2.png"), ResourceLocation.parse("loikvy:textures/screens/button_take_bandage_hover.png")), e -> {
					int x = HealthMenuGUIScreen.this.x;
					int y = HealthMenuGUIScreen.this.y;
					if (HasBandageProcedure.execute(entity)) {
						PacketDistributor.sendToServer(new HealthMenuGUIButtonMessage(0, x, y, z));
						HealthMenuGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (HasBandageProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_button_take_bandage2);
	}
}