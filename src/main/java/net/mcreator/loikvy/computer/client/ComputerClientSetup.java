package net.mcreator.loikvy.computer.client;

import net.mcreator.loikvy.computer.LoikvyComputerRegistry;
import net.mcreator.loikvy.computer.os.AppRegistry;
import net.mcreator.loikvy.computer.os.apps.CalculatorApp;
import net.mcreator.loikvy.pos.StoreControllerApp;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ComputerClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(LoikvyComputerRegistry.COMPUTER_MENU.get(), ComputerScreen::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // Register OS apps that players can open
        AppRegistry.register(net.minecraft.resources.ResourceLocation.parse("loikvy:calculator"), CalculatorApp::new);
        AppRegistry.register(net.minecraft.resources.ResourceLocation.parse("loikvy:store_controller"),
                StoreControllerApp::new);
    }
}
