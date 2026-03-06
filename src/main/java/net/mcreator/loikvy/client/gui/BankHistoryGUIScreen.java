package net.mcreator.loikvy.client.gui;

import net.mcreator.loikvy.utility.networking.client.StorageDataReceivedEvent;
import net.mcreator.loikvy.utility.networking.interfaces.IStorageReceiver;
import net.mcreator.loikvy.utility.networking.packets.RequestStoragePacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.loikvy.world.inventory.BankHistoryGUIMenu;
import net.mcreator.loikvy.init.LoikvyModScreens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

public class BankHistoryGUIScreen extends AbstractContainerScreen<BankHistoryGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;

	public BankHistoryGUIScreen(BankHistoryGUIMenu container, Inventory inventory, Component text) {
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

	private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/bank_history_gui.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.bank_history_gui.label_bank_history"), 58, 6, -12829636, false);
	}

	public void getBankHistory()
	{
		NeoForge.EVENT_BUS.addListener(this::onStorageDataReceived);

		PacketDistributor.sendToServer(new RequestStoragePacket(
				ResourceLocation.fromNamespaceAndPath("minecraft", "bank_history")
		));
	}

	public CompoundTag storageData;

	private void onStorageDataReceived(StorageDataReceivedEvent event) {
		if (event.getStorageId().equals(ResourceLocation.fromNamespaceAndPath("minecraft", "bank_history"))) {
			this.storageData = event.getData();
			postDataGet();
		}
	}

	@Override
	public void removed() {
		super.removed();

		NeoForge.EVENT_BUS.unregister(this);
	}

	@Override
	public void init() {
		super.init();

		getBankHistory();
	}

	public void postDataGet()
	{
		System.out.println("Received storage data: " + storageData);
		System.out.println("Received storage data: " + storageData.getAsString());
	}
}