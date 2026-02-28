package net.mcreator.loikvy.pos;

import net.mcreator.loikvy.computer.network.SyncComputerDataPayload;
import net.mcreator.loikvy.computer.os.AbstractApp;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class StoreControllerApp extends AbstractApp {

    private boolean isOpen = false;
    private StringBuilder priceInput = new StringBuilder();
    private ItemStack selectedItem = ItemStack.EMPTY;
    private Map<ItemStack, Integer> activePrices = new HashMap<>();

    @Override
    public ResourceLocation getId() {
        return ResourceLocation.parse("loikvy:store_controller");
    }

    @Override
    public String getDisplayName() {
        return "Store Manager";
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.parse("minecraft:textures/item/gold_ingot.png");
    }

    @Override
    public void onOpen(int screenWidth, int screenHeight) {
        isOpen = true;
        loadPricesFromNetwork();
    }

    private void loadPricesFromNetwork() {
        if (this.parentScreen instanceof net.mcreator.loikvy.computer.client.ComputerScreen cs) {
            CompoundTag data = cs.getMenu().blockEntity.getOsData();
            if (data.contains("StorePrices")) {
                ListTag list = data.getList("StorePrices", 10);
                activePrices = StoreManager.deserializePrices(list, cs.getMinecraft().level);
            }
        }
    }

    private void syncToServer() {
        if (this.parentScreen instanceof net.mcreator.loikvy.computer.client.ComputerScreen cs) {
            CompoundTag osData = cs.getMenu().blockEntity.getOsData();
            osData.put("StorePrices", StoreManager.serializePrices(activePrices, cs.getMinecraft().level));
            PacketDistributor.sendToServer(new SyncComputerDataPayload(cs.getMenu().blockEntity.getBlockPos(), osData));
        }
    }

    @Override
    public void onClose() {
        isOpen = false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (!isOpen)
            return;

        int x = parentScreen.getGuiLeft() + 10; // AppWindow X
        int y = parentScreen.getGuiTop() + 28; // Below App Header (10 + 18)

        graphics.drawString(this.font, "1. Hold an item with mouse and click here:", x + 5, y + 5, 0xFFDDDDDD, false);

        // Scanner slot area
        int scannerX = x + 5;
        int scannerY = y + 18;
        graphics.fill(scannerX, scannerY, scannerX + 24, scannerY + 24, 0xFF181818);
        graphics.renderOutline(scannerX, scannerY, 24, 24, 0xFF555555);
        if (!selectedItem.isEmpty()) {
            graphics.renderFakeItem(selectedItem, scannerX + 4, scannerY + 4);
        } else {
            graphics.drawString(this.font, "?", scannerX + 9, scannerY + 8, 0xFF666666, false);
        }

        graphics.drawString(this.font,
                "2. Type price: $" + priceInput.toString() + (System.currentTimeMillis() % 1000 < 500 ? "_" : ""),
                x + 35, y + 25, 0xFFFFFFFF, false);

        // Save Button (Styled)
        int saveBtnX = x + 5;
        int saveBtnY = y + 48;
        boolean hoverSave = isHoveringButton(saveBtnX, saveBtnY, mouseX, mouseY);
        graphics.fill(saveBtnX, saveBtnY, saveBtnX + 80, saveBtnY + 16, hoverSave ? 0xFF00AA55 : 0xFF008833);
        graphics.renderOutline(saveBtnX, saveBtnY, 80, 16, 0xFF00DD55);
        String saveText = "Save Item";
        graphics.drawString(this.font, saveText, saveBtnX + 40 - this.font.width(saveText) / 2, saveBtnY + 4,
                0xFFFFFFFF, false);

        // Separator
        graphics.fill(x + 5, y + 70, x + 226, y + 71, 0xFF444444);

        // Listed Prices
        graphics.drawString(this.font, "Listed Prices:", x + 5, y + 78, 0xFFAAAAAA, false);
        int py = y + 92;
        int c = 0;
        for (Map.Entry<ItemStack, Integer> entry : activePrices.entrySet()) {
            if (c > 3)
                break;
            graphics.fill(x + 5, py, x + 226, py + 20, 0xFF222222);
            graphics.renderOutline(x + 5, py, 221, 20, 0xFF333333);
            graphics.renderFakeItem(entry.getKey(), x + 7, py + 2);
            graphics.drawString(this.font, "$" + entry.getValue(), x + 30, py + 6, 0xFF55FF55, false);
            py += 22;
            c++;
        }
    }

    private boolean isHoveringButton(int bx, int by, int mouseX, int mouseY) {
        return mouseX >= bx && mouseX < bx + 80 && mouseY >= by && mouseY < by + 16;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isOpen)
            return false;

        int x = parentScreen.getGuiLeft() + 10;
        int y = parentScreen.getGuiTop() + 28;

        // Click on scanner area
        int scannerX = x + 5;
        int scannerY = y + 18;
        if (mouseX >= scannerX && mouseX <= scannerX + 24 && mouseY >= scannerY && mouseY <= scannerY + 24) {
            ItemStack held = this.parentScreen.getMenu().getCarried();
            if (!held.isEmpty()) {
                selectedItem = held.copy();
                selectedItem.setCount(1);
            }
            return true;
        }

        // Click 'Save' button
        int saveBtnX = x + 5;
        int saveBtnY = y + 48;
        if (isHoveringButton(saveBtnX, saveBtnY, (int) mouseX, (int) mouseY)) {
            if (!selectedItem.isEmpty() && priceInput.length() > 0) {
                try {
                    int price = Integer.parseInt(priceInput.toString());
                    activePrices.put(selectedItem.copy(), price);
                    syncToServer();
                    priceInput.setLength(0);
                } catch (Exception ignored) {
                }
            }
            return true;
        }

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
        if (!isOpen)
            return false;
        if (keyCode == 259 && priceInput.length() > 0) { // BACKSPACE
            priceInput.deleteCharAt(priceInput.length() - 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (!isOpen)
            return false;
        if (Character.isDigit(codePoint)) {
            priceInput.append(codePoint);
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
