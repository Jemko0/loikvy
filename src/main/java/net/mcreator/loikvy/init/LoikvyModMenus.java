/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.loikvy.world.inventory.StoveGUIMenu;
import net.mcreator.loikvy.world.inventory.StoreGUIMenu;
import net.mcreator.loikvy.world.inventory.SmokeDetectorGUIMenu;
import net.mcreator.loikvy.world.inventory.SlotsGUIMenu;
import net.mcreator.loikvy.world.inventory.ReceiptMakerMenu;
import net.mcreator.loikvy.world.inventory.PlayerStatGUIMenu;
import net.mcreator.loikvy.world.inventory.PhoneGUIMenu;
import net.mcreator.loikvy.world.inventory.KeyCreationGUIMenu;
import net.mcreator.loikvy.world.inventory.HealthMenuGUIMenu;
import net.mcreator.loikvy.world.inventory.FridgeGUIMenu;
import net.mcreator.loikvy.world.inventory.FreezerGUIMenu;
import net.mcreator.loikvy.world.inventory.DoorLockIDGUIMenu;
import net.mcreator.loikvy.world.inventory.DeathGUIMenu;
import net.mcreator.loikvy.world.inventory.CookingPanGUIMenu;
import net.mcreator.loikvy.world.inventory.CoalGeneratorGUIMenu;
import net.mcreator.loikvy.world.inventory.CalendarGUIMenu;
import net.mcreator.loikvy.world.inventory.BookEditGUIMenu;
import net.mcreator.loikvy.world.inventory.BlackJackGUIMenu;
import net.mcreator.loikvy.world.inventory.BasicClockGUIMenu;
import net.mcreator.loikvy.world.inventory.BankHistoryGUIMenu;
import net.mcreator.loikvy.network.MenuStateUpdateMessage;
import net.mcreator.loikvy.LoikvyMod;

import java.util.Map;

public class LoikvyModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, LoikvyMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<CalendarGUIMenu>> CALENDAR_GUI = REGISTRY.register("calendar_gui", () -> IMenuTypeExtension.create(CalendarGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<BasicClockGUIMenu>> BASIC_CLOCK_GUI = REGISTRY.register("basic_clock_gui", () -> IMenuTypeExtension.create(BasicClockGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<PlayerStatGUIMenu>> PLAYER_STAT_GUI = REGISTRY.register("player_stat_gui", () -> IMenuTypeExtension.create(PlayerStatGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DeathGUIMenu>> DEATH_GUI = REGISTRY.register("death_gui", () -> IMenuTypeExtension.create(DeathGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<FridgeGUIMenu>> FRIDGE_GUI = REGISTRY.register("fridge_gui", () -> IMenuTypeExtension.create(FridgeGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<FreezerGUIMenu>> FREEZER_GUI = REGISTRY.register("freezer_gui", () -> IMenuTypeExtension.create(FreezerGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<KeyCreationGUIMenu>> KEY_CREATION_GUI = REGISTRY.register("key_creation_gui", () -> IMenuTypeExtension.create(KeyCreationGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DoorLockIDGUIMenu>> DOOR_LOCK_IDGUI = REGISTRY.register("door_lock_idgui", () -> IMenuTypeExtension.create(DoorLockIDGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CookingPanGUIMenu>> COOKING_PAN_GUI = REGISTRY.register("cooking_pan_gui", () -> IMenuTypeExtension.create(CookingPanGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SmokeDetectorGUIMenu>> SMOKE_DETECTOR_GUI = REGISTRY.register("smoke_detector_gui", () -> IMenuTypeExtension.create(SmokeDetectorGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CoalGeneratorGUIMenu>> COAL_GENERATOR_GUI = REGISTRY.register("coal_generator_gui", () -> IMenuTypeExtension.create(CoalGeneratorGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<PhoneGUIMenu>> PHONE_GUI = REGISTRY.register("phone_gui", () -> IMenuTypeExtension.create(PhoneGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<StoveGUIMenu>> STOVE_GUI = REGISTRY.register("stove_gui", () -> IMenuTypeExtension.create(StoveGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<StoreGUIMenu>> STORE_GUI = REGISTRY.register("store_gui", () -> IMenuTypeExtension.create(StoreGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<BookEditGUIMenu>> BOOK_EDIT_GUI = REGISTRY.register("book_edit_gui", () -> IMenuTypeExtension.create(BookEditGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ReceiptMakerMenu>> RECEIPT_MAKER = REGISTRY.register("receipt_maker", () -> IMenuTypeExtension.create(ReceiptMakerMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<BankHistoryGUIMenu>> BANK_HISTORY_GUI = REGISTRY.register("bank_history_gui", () -> IMenuTypeExtension.create(BankHistoryGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SlotsGUIMenu>> SLOTS_GUI = REGISTRY.register("slots_gui", () -> IMenuTypeExtension.create(SlotsGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<BlackJackGUIMenu>> BLACK_JACK_GUI = REGISTRY.register("black_jack_gui", () -> IMenuTypeExtension.create(BlackJackGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<HealthMenuGUIMenu>> HEALTH_MENU_GUI = REGISTRY.register("health_menu_gui", () -> IMenuTypeExtension.create(HealthMenuGUIMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof LoikvyModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				PacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}