package net.mcreator.loikvy.computer.os.apps;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.mcreator.loikvy.computer.os.AbstractApp;

public class CalculatorApp extends AbstractApp {

    private boolean isOpen = false;
    private StringBuilder currentInput = new StringBuilder();
    private Double storedValue = null;
    private char pendingOp = ' ';

    @Override
    public ResourceLocation getId() {
        return ResourceLocation.parse("loikvy:calculator");
    }

    @Override
    public String getDisplayName() {
        return "Calculator";
    }

    @Override
    public ResourceLocation getIcon() {
        // Needs a valid path. I'll default to a generic item texture if none exists.
        return ResourceLocation.parse("minecraft:textures/item/diamond.png");
    }

    @Override
    public void onOpen(int screenWidth, int screenHeight) {
        isOpen = true;
    }

    @Override
    public void onClose() {
        isOpen = false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (!isOpen)
            return;

        // Draw a fake "screen" for the calculator inside the UI bounds
        int x = parentScreen.getGuiLeft() + 20;
        int y = parentScreen.getGuiTop() + 20;

        graphics.fill(x, y, x + 100, y + 100, 0xFF333333); // Background
        graphics.drawString(this.font, currentInput.length() > 0 ? currentInput.toString() : "0", x + 5, y + 5,
                0xFFFFFF, false);
        graphics.drawString(this.font, "Type keys to calc", x + 5, y + 80, 0x888888, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Return false to let default logic handle ESC
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (!isOpen)
            return false;

        if (Character.isDigit(codePoint) || codePoint == '.') {
            currentInput.append(codePoint);
            return true;
        } else if (codePoint == '+' || codePoint == '-' || codePoint == '*' || codePoint == '/') {
            if (currentInput.length() > 0) {
                try {
                    storedValue = Double.parseDouble(currentInput.toString());
                } catch (Exception ignored) {
                }
                currentInput.setLength(0);
                pendingOp = codePoint;
            }
            return true;
        } else if (codePoint == '=') {
            if (storedValue != null && currentInput.length() > 0) {
                try {
                    double val = Double.parseDouble(currentInput.toString());
                    double res = 0;
                    switch (pendingOp) {
                        case '+':
                            res = storedValue + val;
                            break;
                        case '-':
                            res = storedValue - val;
                            break;
                        case '*':
                            res = storedValue * val;
                            break;
                        case '/':
                            res = storedValue / val;
                            break;
                    }
                    currentInput.setLength(0);
                    currentInput.append(res);
                    pendingOp = ' ';
                    storedValue = null;
                } catch (Exception ignored) {
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public void save(CompoundTag appData) {
    }

    @Override
    public void load(CompoundTag appData) {
    }
}
