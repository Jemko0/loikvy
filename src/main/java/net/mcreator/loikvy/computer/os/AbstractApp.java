package net.mcreator.loikvy.computer.os;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;

/**
 * A helper class for apps that gives them access to the parent screen's
 * resources
 * to create widgets (EditBox, Button, etc.) and handle slot positioning.
 */
public abstract class AbstractApp implements ComputerApp {

    protected AbstractContainerScreen<?> parentScreen;
    protected Font font;

    /**
     * Called by the ComputerScreen when the OS loads this app instance.
     */
    public void init(AbstractContainerScreen<?> screen, Font font) {
        this.parentScreen = screen;
        this.font = font;
    }
}
