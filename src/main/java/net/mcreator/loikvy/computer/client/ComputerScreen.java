package net.mcreator.loikvy.computer.client;

import net.mcreator.loikvy.computer.inventory.ComputerMenu;
import net.mcreator.loikvy.computer.os.AbstractApp;
import net.mcreator.loikvy.computer.os.AppRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ComputerScreen extends AbstractContainerScreen<ComputerMenu> {

    private final ResourceLocation OS_BACKGROUND = ResourceLocation.parse("loikvy:textures/gui/computer_bg.png");

    // Abstracted app states
    private AbstractApp currentApp = null;
    private List<AppIcon> desktopIcons = new ArrayList<>();

    public ComputerScreen(ComputerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 220;
    }

    @Override
    protected void init() {
        super.init();
        loadDesktopIcons();
        if (currentApp != null) {
            currentApp.onOpen(this.width, this.height);
        }
    }

    private void loadDesktopIcons() {
        desktopIcons.clear();
        int iconX = 10;
        int iconY = 10;
        int count = 0;
        for (Map.Entry<ResourceLocation, Supplier<AbstractApp>> entry : AppRegistry.getRegisteredApps().entrySet()) {
            desktopIcons.add(new AppIcon(entry.getKey(), this.leftPos + iconX, this.topPos + iconY));
            iconX += 40;
            if (count > 0 && count % 5 == 0) {
                iconX = 10;
                iconY += 40;
            }
            count++;
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        int w = this.imageWidth;
        int h = this.imageHeight;

        // Desktop Background (slate dark blue)
        graphics.fill(x, y, x + w, y + h, 0xFF1B2838);

        // Taskbar at the bottom
        int taskbarY = y + h - 20;
        graphics.fill(x, taskbarY, x + w, taskbarY + 20, 0xFF0F1621);
        graphics.fill(x, taskbarY, x + w, taskbarY + 1, 0xFF314357); // Top border of taskbar

        // "Start" button / OS Logo on left
        graphics.fill(x + 5, taskbarY + 4, x + 17, taskbarY + 16, 0xFF00A2ED);

        String osName = "LoikvyOS";
        graphics.drawString(this.font, osName, x + w - this.font.width(osName) - 5, taskbarY + 6, 0xDDDDDD, false);

        for (AppIcon icon : desktopIcons) {
            icon.render(graphics, mouseX, mouseY);
        }

        if (currentApp != null) {
            // App Window bounds
            int appW = w - 20;
            int appH = h - 40;
            int appX = x + 10;
            int appY = y + 10;

            // App Window shadow
            graphics.fill(appX + 3, appY + 3, appX + appW + 3, appY + appH + 3, 0x66000000);

            // App Window background
            graphics.fill(appX, appY, appX + appW, appY + appH, 0xFF2B2B2B);

            // App Header
            int headerH = 18;
            graphics.fill(appX, appY, appX + appW, appY + headerH, 0xFF202020);
            graphics.drawString(this.font, currentApp.getDisplayName(), appX + 5, appY + 5, 0xFFEEEEEE, false);

            // Close button [X]
            int closeBtnX = appX + appW - 16;
            int closeBtnY = appY + 1;
            boolean hoverClose = mouseX >= closeBtnX && mouseX < closeBtnX + 15 && mouseY >= closeBtnY
                    && mouseY < closeBtnY + 15;
            graphics.fill(closeBtnX, closeBtnY, closeBtnX + 15, closeBtnY + 15, hoverClose ? 0xFFFF4444 : 0xFF202020);
            graphics.drawString(this.font, "X", closeBtnX + 5, closeBtnY + 4, 0xFFFFFFFF, false);

            // Outline around the app window
            graphics.renderOutline(appX, appY, appW, appH, 0xFF444444);
            graphics.fill(appX, appY + headerH, appX + appW, appY + headerH + 1, 0xFF444444); // Separator

            // Draw App Window Content
            currentApp.render(graphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (currentApp != null) {
            int closeBtnX = this.leftPos + 10 + this.imageWidth - 20 - 16;
            int closeBtnY = this.topPos + 10 + 1;

            if (mouseY >= closeBtnY && mouseY <= closeBtnY + 15 && mouseX >= closeBtnX && mouseX <= closeBtnX + 15) {
                currentApp.onClose();
                currentApp = null;
                return true;
            }
            if (currentApp.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        } else {
            // Click Desktop icons
            for (AppIcon icon : desktopIcons) {
                if (icon.isHovered(mouseX, mouseY)) {
                    launchApp(icon.appId);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (currentApp != null && currentApp.mouseReleased(mouseX, mouseY, button))
            return true;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (currentApp != null && currentApp.mouseDragged(mouseX, mouseY, button, dragX, dragY))
            return true;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (currentApp != null && currentApp.keyPressed(keyCode, scanCode, modifiers))
            return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (currentApp != null && currentApp.charTyped(codePoint, modifiers))
            return true;
        return super.charTyped(codePoint, modifiers);
    }

    private void launchApp(ResourceLocation appId) {
        AbstractApp newApp = AppRegistry.createAppInstance(appId);
        if (newApp != null) {
            this.currentApp = newApp;
            this.currentApp.init(this, this.font);
            this.currentApp.onOpen(this.width, this.height);
        }
    }

    private class AppIcon {
        ResourceLocation appId;
        int x, y;
        AbstractApp tempInstance;

        AppIcon(ResourceLocation appId, int x, int y) {
            this.appId = appId;
            this.x = x;
            this.y = y;
            this.tempInstance = AppRegistry.createAppInstance(appId);
        }

        boolean isHovered(double mx, double my) {
            return mx >= x && mx < x + 32 && my >= y && my < y + 32;
        }

        void render(GuiGraphics graphics, int mx, int my) {
            int color = isHovered(mx, my) ? 0x88AAAAAA : 0xFFFFFFFF;
            // Draw a generic square if icon missing
            graphics.fill(x, y, x + 32, y + 32, color);
            String name = tempInstance != null ? tempInstance.getDisplayName() : "Unknown";
            graphics.drawString(font, name, x - font.width(name) / 2 + 16, y + 35, 0xFFFFFF);
        }
    }
}
