package net.mcreator.loikvy.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.loikvy.world.inventory.PhoneGUIMenu;
import net.mcreator.loikvy.procedures.GetWifiLevelProcedure;
import net.mcreator.loikvy.procedures.GetPhoneModelNameProcedure;
import net.mcreator.loikvy.procedures.GetClockTimeFormattedProcedure;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class PhoneGUIScreen extends AbstractContainerScreen<PhoneGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	ImageButton imagebutton_phone_button_contacts;

	public PhoneGUIScreen(PhoneGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 118;
		this.imageHeight = 199;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/phone_gui.png");

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
		guiGraphics.blit(ResourceLocation.parse("loikvy:textures/screens/wifi_sprite_24_bicubic.png"), this.leftPos + 90, this.topPos + 4, Mth.clamp((int) GetWifiLevelProcedure.execute(entity) * 24, 0, 96), 0, 24, 24, 120, 24);
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
		guiGraphics.drawString(this.font, GetPhoneModelNameProcedure.execute(entity), 5, 6, -12829636, false);
		guiGraphics.drawString(this.font, GetClockTimeFormattedProcedure.execute(), 5, 19, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.phone_gui.label_contacts"), 6, 65, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.phone_gui.label_empty"), 5, 33, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_phone_button_contacts = new ImageButton(this.leftPos + 18, this.topPos + 48, 16, 16,
				new WidgetSprites(ResourceLocation.parse("loikvy:textures/screens/phone_button_contacts.png"), ResourceLocation.parse("loikvy:textures/screens/phone_button_contacts_hovered.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_phone_button_contacts);
	}
}