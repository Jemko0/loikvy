package net.mcreator.loikvy.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.mcreator.loikvy.world.inventory.BankHistoryGUIMenu;
import net.mcreator.loikvy.init.LoikvyModScreens;
import com.mojang.blaze3d.systems.RenderSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BankHistoryGUIScreen extends AbstractContainerScreen<BankHistoryGUIMenu> implements LoikvyModScreens.ScreenAccessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankHistoryGUIScreen.class);
    
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private boolean menuStateUpdateActive = false;
    Button button_next;
    Button button_previous;
    
    // Pagination variables
    private List<BankTransaction> bankHistory = new ArrayList<>();
    private int currentPage = 0;
    private final int itemsPerPage = 8;
    
    // Transaction data class
    public static class BankTransaction {
        public final String name;
        public final int amount;
        public final String date;
        
        public BankTransaction(String name, int amount, String date) {
            this.name = name != null ? name : "Unknown";
            this.amount = amount;
            this.date = date != null ? date : "No Date";
        }
    }
    
    public BankHistoryGUIScreen(BankHistoryGUIMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 300;
        this.imageHeight = 200;
        
        // Load bank history data
        loadBankHistory();
    }
    
    private void loadBankHistory() {
        bankHistory.clear();
        System.out.println("Loading bank history for player: " + entity.getName().getString());
        
        try {
            // GUI runs on client side, but we need server data
            // We need to access the server through Minecraft instance
            if (minecraft != null && minecraft.getSingleplayerServer() != null) {
                // Single player - we can access the server directly
                var server = minecraft.getSingleplayerServer();
                var commandStorage = server.getCommandStorage();
                ResourceLocation storageId = ResourceLocation.parse("minecraft:bank_history");
                CompoundTag storageData = commandStorage.get(storageId);
                
                System.out.println("Storage data found: " + (storageData != null));
                
                if (storageData != null) {
                    System.out.println("Storage contents: " + storageData.toString());
                    System.out.println("Storage keys: " + storageData.getAllKeys());
                    System.out.println("Looking for player key: " + entity.getName().getString());
                    
                    // Try different possible key formats
                    String playerName = entity.getName().getString();
                    String playerUUID = entity.getUUID().toString();
                    
                    System.out.println("Player name: " + playerName + ", Player UUID: " + playerUUID);
                    
                    // Check if it's stored as a list directly under the player name
                    if (storageData.get(playerName) instanceof ListTag) {
                        ListTag playerHistory = storageData.getList(playerName, Tag.TAG_COMPOUND);
                        System.out.println("Found " + playerHistory.size() + " transactions for player");
                        
                        for (int i = 0; i < playerHistory.size(); i++) {
                            CompoundTag transaction = playerHistory.getCompound(i);
                            System.out.println("Transaction " + i + ": " + transaction.toString());
                            
                            String name = transaction.contains("name") ? transaction.getString("name") : "Unknown";
                            int amount = transaction.contains("amount") ? transaction.getInt("amount") : 0;
                            String date = transaction.contains("date") ? transaction.getString("date") : "No Date";
                            
                            bankHistory.add(new BankTransaction(name, amount, date));
                            System.out.println("Added transaction: " + name + " - " + amount + " - " + date);
                        }
                    } else {
                        System.out.println("Player data not found or not a list for key: " + playerName);
                    }
                } else {
                    System.out.println("No storage data found for minecraft:bank_history");
                }
            } else {
                System.out.println("Cannot access server data - multiplayer or server not available");
                // For multiplayer, you'd need to send a packet to request data from server
                loadSimulatedData();
            }
            
        } catch (Exception e) {
            System.out.println("Error loading bank history: " + e.getMessage());
            e.printStackTrace();
            loadSimulatedData();
        }
    }
    
    // This is a placeholder - replace with actual data loading from your storage system
    private void loadSimulatedData() {
        // Example data based on your format
        bankHistory.add(new BankTransaction("Sinvoller Shop", 20, "8/6/1951"));
        bankHistory.add(new BankTransaction("Loikvy Hausbau", 20, "8/6/1951"));
        bankHistory.add(new BankTransaction("Test Transaction", 50, "9/6/1951"));
        bankHistory.add(new BankTransaction("Another Shop", -15, "10/6/1951"));
    }
    
    // Alternative method to parse data from command output if needed
    public void parseBankHistoryFromCommand(String commandOutput) {
        bankHistory.clear();
        
        try {
            // Parse the command output format
            // "Storage minecraft:bank_history has the following contents: [{amount: 20, name: "Sinvoller Shop"}, {date: "8/6/1951", amount: 20, name: "Loikvy Hausbau"}]"
            
            int startIndex = commandOutput.indexOf("[");
            int endIndex = commandOutput.lastIndexOf("]");
            
            if (startIndex != -1 && endIndex != -1) {
                String dataString = commandOutput.substring(startIndex + 1, endIndex);
                // This is a simplified parser - you might want to use a proper JSON library
                String[] transactions = dataString.split("}, \\{");
                
                for (String transaction : transactions) {
                    transaction = transaction.replace("{", "").replace("}", "");
                    String[] parts = transaction.split(", ");
                    
                    String name = "Unknown";
                    int amount = 0;
                    String date = "No Date";
                    
                    for (String part : parts) {
                        if (part.contains("name:")) {
                            name = part.split("name: \"")[1].replace("\"", "");
                        } else if (part.contains("amount:")) {
                            amount = Integer.parseInt(part.split("amount: ")[1]);
                        } else if (part.contains("date:")) {
                            date = part.split("date: \"")[1].replace("\"", "");
                        }
                    }
                    
                    bankHistory.add(new BankTransaction(name, amount, date));
                }
            }
        } catch (Exception e) {
            // If parsing fails, show error or load default data
            bankHistory.add(new BankTransaction("Error loading data", 0, ""));
        }
    }
    
    @Override
    public void updateMenuState(int elementType, String name, Object elementState) {
        menuStateUpdateActive = true;
        menuStateUpdateActive = false;
    }
    
    private static final ResourceLocation texture = ResourceLocation.parse("loikvy:textures/screens/bank_history_gui.png");
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
    
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }
    
    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }
    
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.loikvy.bank_history_gui.label_bank_history"), 5, 5, -12829636, false);
        
        // Render bank history list
        renderBankHistoryList(guiGraphics);
        
        // Render page info
        int totalPages = Math.max(1, (int) Math.ceil((double) bankHistory.size() / itemsPerPage));
        String pageInfo = "Page " + (currentPage + 1) + " of " + totalPages;
        guiGraphics.drawString(this.font, Component.literal(pageInfo), 125, 175, -1, false);
    }
    
    private void renderBankHistoryList(GuiGraphics guiGraphics) {
        int startIndex = currentPage * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, bankHistory.size());
        
        // Column headers
        guiGraphics.drawString(this.font, Component.literal("Transaction"), 10, 25, -1, false);
        guiGraphics.drawString(this.font, Component.literal("Amount"), 150, 25, -1, false);
        guiGraphics.drawString(this.font, Component.literal("Date"), 200, 25, -1, false);
        
        // Draw a line under headers
        guiGraphics.fill(10, 35, 290, 36, -6250336);
        
        // Render transactions for current page
        for (int i = startIndex; i < endIndex; i++) {
            BankTransaction transaction = bankHistory.get(i);
            int yPos = 45 + (i - startIndex) * 15;
            
            // Transaction name (truncate if too long)
            String displayName = transaction.name;
            if (displayName.length() > 20) {
                displayName = displayName.substring(0, 17) + "...";
            }
            guiGraphics.drawString(this.font, Component.literal(displayName), 10, yPos, -1, false);
            
            // Amount (color based on positive/negative)
            int amountColor = transaction.amount >= 0 ? -16711936 : -65536; // Green for positive, red for negative
            String amountText = (transaction.amount >= 0 ? "+" : "") + transaction.amount;
            guiGraphics.drawString(this.font, Component.literal(amountText), 150, yPos, amountColor, false);
            
            // Date
            guiGraphics.drawString(this.font, Component.literal(transaction.date), 200, yPos, -1, false);
        }
        
        // Show "No transactions" if empty
        if (bankHistory.isEmpty()) {
            guiGraphics.drawString(this.font, Component.literal("No transactions found"), 50, 50, -6250336, false);
        }
    }
    
    @Override
    public void init() {
        super.init();
        
        button_next = Button.builder(Component.translatable("gui.loikvy.bank_history_gui.button_next"), e -> {
            int totalPages = Math.max(1, (int) Math.ceil((double) bankHistory.size() / itemsPerPage));
            if (currentPage < totalPages - 1) {
                currentPage++;
            }
        }).bounds(this.leftPos + 249, this.topPos + 175, 46, 20).build();
        this.addRenderableWidget(button_next);
        
        button_previous = Button.builder(Component.translatable("gui.loikvy.bank_history_gui.button_previous"), e -> {
            if (currentPage > 0) {
                currentPage--;
            }
        }).bounds(this.leftPos + 5, this.topPos + 174, 46, 20).build();
        this.addRenderableWidget(button_previous);
    }
    
    @Override
    public void containerTick() {
        super.containerTick();
        
        // Update button states based on pagination
        if (button_next != null && button_previous != null) {
            int totalPages = Math.max(1, (int) Math.ceil((double) bankHistory.size() / itemsPerPage));
            button_next.active = currentPage < totalPages - 1;
            button_previous.active = currentPage > 0;
        }
    }
}