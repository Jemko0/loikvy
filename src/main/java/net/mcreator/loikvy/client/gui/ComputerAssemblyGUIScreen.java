package net.mcreator.loikvy.client.gui;

import com.daqem.uilib.api.client.gui.background.IBackground;
import com.daqem.uilib.client.gui.component.AbstractSpriteComponent;
import com.daqem.uilib.client.gui.component.ButtonComponent;
import com.daqem.uilib.client.gui.component.texture.NineSlicedTextureComponent;
import com.daqem.uilib.client.gui.texture.NineSlicedTexture;
import net.mcreator.loikvy.client.gui.lylib.SlotBackgroundComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.mcreator.loikvy.world.inventory.ComputerAssemblyGUIMenu;
import net.mcreator.loikvy.init.LoikvyModScreens;

import java.util.LinkedList;
import java.util.List;

public class ComputerAssemblyGUIScreen extends com.daqem.uilib.client.gui.AbstractContainerScreen<ComputerAssemblyGUIMenu> implements LoikvyModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;

	public ComputerAssemblyGUIScreen(ComputerAssemblyGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 240;
		this.imageHeight = 177;
	}

	@Override
	public void startScreen()
	{
		NineSlicedTexture nct = new NineSlicedTexture(ResourceLocation.fromNamespaceAndPath("loikvy", "textures/screens/pc_background_nineslice.png"), 0, 0, 8, 8, 2, 2);

		NineSlicedTextureComponent bg = new NineSlicedTextureComponent(
				nct,
				leftPos,
				topPos,
				imageWidth,
				imageHeight
		);

		this.addComponent(bg);

		SlotBackgroundComponent slotBg = new SlotBackgroundComponent(
				this.menu.slots,
				leftPos,
				topPos,
				imageWidth,
				imageHeight
		);
		this.addComponent(slotBg);
	}

	@Override
	public void onTickScreen(GuiGraphics guiGraphics, int i, int i1, float v) {

	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}
}