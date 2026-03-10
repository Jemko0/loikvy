package net.mcreator.loikvy.client.gui.lylib;

import com.daqem.uilib.client.gui.component.AbstractComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import java.util.List;

public class SlotBackgroundComponent extends AbstractComponent<SlotBackgroundComponent> {
    private final List<Slot> slots;
    private final int leftPos;
    private final int topPos;

    private static final ResourceLocation SLOT_TEXTURE = ResourceLocation.fromNamespaceAndPath("loikvy", "textures/gui/slot.png");

    public SlotBackgroundComponent(List<Slot> slots, int leftPos, int topPos, int width, int height) {
        super(null, leftPos, topPos, width, height);  // Pass null for texture
        this.slots = slots;
        this.leftPos = leftPos;
        this.topPos = topPos;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (Slot slot : slots) {
            if (slot.getItem().isEmpty()) {  // Only render empty slots for better performance
                int x = slot.x - 1;
                int y = slot.y - 1;

                // Fill uses screen coordinates, not relative to this component
                // So we don't add leftPos/topPos here since the component already handles positioning
                guiGraphics.blit(SLOT_TEXTURE, x, y, 0, 0, 18, 18, 18, 18);
            }
        }
    }

    @Override
    public SlotBackgroundComponent getClone() {
        return null;
    }
}