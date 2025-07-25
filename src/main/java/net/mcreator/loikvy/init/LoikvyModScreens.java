/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import org.joml.Vector3f;
import org.joml.Quaternionf;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.loikvy.client.gui.StoveGUIScreen;
import net.mcreator.loikvy.client.gui.StoreGUIScreen;
import net.mcreator.loikvy.client.gui.SmokeDetectorGUIScreen;
import net.mcreator.loikvy.client.gui.SlotsGUIScreen;
import net.mcreator.loikvy.client.gui.ReceiptMakerScreen;
import net.mcreator.loikvy.client.gui.PlayerStatGUIScreen;
import net.mcreator.loikvy.client.gui.PhoneGUIScreen;
import net.mcreator.loikvy.client.gui.KeyCreationGUIScreen;
import net.mcreator.loikvy.client.gui.HealthMenuGUIScreen;
import net.mcreator.loikvy.client.gui.FridgeGUIScreen;
import net.mcreator.loikvy.client.gui.FreezerGUIScreen;
import net.mcreator.loikvy.client.gui.DoorLockIDGUIScreen;
import net.mcreator.loikvy.client.gui.DeathGUIScreen;
import net.mcreator.loikvy.client.gui.CookingPanGUIScreen;
import net.mcreator.loikvy.client.gui.CoalGeneratorGUIScreen;
import net.mcreator.loikvy.client.gui.CalendarGUIScreen;
import net.mcreator.loikvy.client.gui.BookEditGUIScreen;
import net.mcreator.loikvy.client.gui.BlackJackGUIScreen;
import net.mcreator.loikvy.client.gui.BasicClockGUIScreen;
import net.mcreator.loikvy.client.gui.BankHistoryGUIScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LoikvyModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(LoikvyModMenus.CALENDAR_GUI.get(), CalendarGUIScreen::new);
		event.register(LoikvyModMenus.BASIC_CLOCK_GUI.get(), BasicClockGUIScreen::new);
		event.register(LoikvyModMenus.PLAYER_STAT_GUI.get(), PlayerStatGUIScreen::new);
		event.register(LoikvyModMenus.DEATH_GUI.get(), DeathGUIScreen::new);
		event.register(LoikvyModMenus.FRIDGE_GUI.get(), FridgeGUIScreen::new);
		event.register(LoikvyModMenus.FREEZER_GUI.get(), FreezerGUIScreen::new);
		event.register(LoikvyModMenus.KEY_CREATION_GUI.get(), KeyCreationGUIScreen::new);
		event.register(LoikvyModMenus.DOOR_LOCK_IDGUI.get(), DoorLockIDGUIScreen::new);
		event.register(LoikvyModMenus.COOKING_PAN_GUI.get(), CookingPanGUIScreen::new);
		event.register(LoikvyModMenus.SMOKE_DETECTOR_GUI.get(), SmokeDetectorGUIScreen::new);
		event.register(LoikvyModMenus.COAL_GENERATOR_GUI.get(), CoalGeneratorGUIScreen::new);
		event.register(LoikvyModMenus.PHONE_GUI.get(), PhoneGUIScreen::new);
		event.register(LoikvyModMenus.STOVE_GUI.get(), StoveGUIScreen::new);
		event.register(LoikvyModMenus.STORE_GUI.get(), StoreGUIScreen::new);
		event.register(LoikvyModMenus.BOOK_EDIT_GUI.get(), BookEditGUIScreen::new);
		event.register(LoikvyModMenus.RECEIPT_MAKER.get(), ReceiptMakerScreen::new);
		event.register(LoikvyModMenus.BANK_HISTORY_GUI.get(), BankHistoryGUIScreen::new);
		event.register(LoikvyModMenus.SLOTS_GUI.get(), SlotsGUIScreen::new);
		event.register(LoikvyModMenus.BLACK_JACK_GUI.get(), BlackJackGUIScreen::new);
		event.register(LoikvyModMenus.HEALTH_MENU_GUI.get(), HealthMenuGUIScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}

	public static void renderEntityInInventoryFollowsAngle(GuiGraphics guiGraphics, int x, int y, int scale, float angleXComponent, float angleYComponent, LivingEntity entity) {
		Quaternionf pose = new Quaternionf().rotateZ((float) Math.PI);
		Quaternionf cameraOrientation = new Quaternionf().rotateX(angleYComponent * 20 * ((float) Math.PI / 180F));
		pose.mul(cameraOrientation);
		float f2 = entity.yBodyRot;
		float f3 = entity.getYRot();
		float f4 = entity.getXRot();
		float f5 = entity.yHeadRotO;
		float f6 = entity.yHeadRot;
		entity.yBodyRot = 180.0F + angleXComponent * 20.0F;
		entity.setYRot(180.0F + angleXComponent * 40.0F);
		entity.setXRot(-angleYComponent * 20.0F);
		entity.yHeadRot = entity.getYRot();
		entity.yHeadRotO = entity.getYRot();
		InventoryScreen.renderEntityInInventory(guiGraphics, x, y, scale, new Vector3f(0, 0, 0), pose, cameraOrientation, entity);
		entity.yBodyRot = f2;
		entity.setYRot(f3);
		entity.setXRot(f4);
		entity.yHeadRotO = f5;
		entity.yHeadRot = f6;
	}
}