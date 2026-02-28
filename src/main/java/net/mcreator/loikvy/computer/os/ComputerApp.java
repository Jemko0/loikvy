package net.mcreator.loikvy.computer.os;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;

/**
 * Represents a program that can be installed on a Computer.
 */
public interface ComputerApp {

    /**
     * The unique ID of this app. E.g. "loikvy:store_controller"
     */
    ResourceLocation getId();

    /**
     * The localized or display name of the app shown on the desktop.
     */
    String getDisplayName();

    /**
     * The icon texture location for the desktop.
     * Should be a 16x16 or 32x32 texture.
     */
    ResourceLocation getIcon();

    /**
     * Called when the app is opened so it can initialize its UI state.
     */
    void onOpen(int screenWidth, int screenHeight);

    /**
     * Called when the app is closed to clean up state.
     */
    void onClose();

    /**
     * Renders the app's custom UI.
     * Return true if the app handled rendering the background,
     * false if the default desktop window background should be drawn.
     */
    void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);

    /**
     * Handle mouse clicks within the app.
     * Return true if the click was consumed.
     */
    boolean mouseClicked(double mouseX, double mouseY, int button);

    /**
     * Handle mouse releases within the app.
     * Return true if the release was consumed.
     */
    boolean mouseReleased(double mouseX, double mouseY, int button);

    /**
     * Handle dragging within the app.
     */
    boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY);

    /**
     * Handle keyboard input within the app.
     */
    boolean keyPressed(int keyCode, int scanCode, int modifiers);

    /**
     * Handle character typing (for text boxes).
     */
    boolean charTyped(char codePoint, int modifiers);

    /**
     * Save app-specific state data to the computer's NBT.
     */
    void save(CompoundTag appData);

    /**
     * Load app-specific state data from the computer's NBT.
     */
    void load(CompoundTag appData);
}
