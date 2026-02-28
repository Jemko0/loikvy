package net.mcreator.loikvy.pos;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreManager {

    /**
     * Serializes a store's price map to NBT.
     */
    public static ListTag serializePrices(Map<ItemStack, Integer> prices, Level level) {
        ListTag list = new ListTag();
        for (Map.Entry<ItemStack, Integer> entry : prices.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            // In 1.21.1, items form an NBT tree with components via saveOptional
            entryTag.put("Item", entry.getKey().saveOptional(level.registryAccess()));
            entryTag.putInt("Price", entry.getValue());
            list.add(entryTag);
        }
        return list;
    }

    /**
     * Deserializes a store's price map from NBT.
     */
    public static Map<ItemStack, Integer> deserializePrices(ListTag list, Level level) {
        Map<ItemStack, Integer> prices = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entryTag = list.getCompound(i);
            ItemStack stack = ItemStack.parseOptional(level.registryAccess(), entryTag.getCompound("Item"));
            int price = entryTag.getInt("Price");
            if (!stack.isEmpty()) {
                prices.put(stack, price);
            }
        }
        return prices;
    }

    /**
     * Finds the price for an item, respecting strict component matching if
     * required.
     */
    public static int getPriceForItem(Map<ItemStack, Integer> prices, ItemStack target) {
        for (Map.Entry<ItemStack, Integer> entry : prices.entrySet()) {
            ItemStack pricedItem = entry.getKey();
            // Strict matching: requires exact exact same item and components
            if (ItemStack.isSameItemSameComponents(pricedItem, target)) {
                return entry.getValue();
            }
        }
        return -1; // -1 means not priced
    }

    /**
     * Retrieves the player's current money score.
     */
    public static int getPlayerMoney(Player player) {
        Scoreboard scoreboard = player.level().getScoreboard();
        Objective moneyObj = scoreboard.getObjective("Money");
        if (moneyObj != null) {
            return scoreboard.getOrCreatePlayerScore(ScoreHolder.forNameOnly(player.getScoreboardName()), moneyObj)
                    .get();
        }
        return 0;
    }

    /**
     * Modifies the player's current money score.
     */
    public static void modifyPlayerMoney(Player player, int amount) {
        Scoreboard scoreboard = player.level().getScoreboard();
        Objective moneyObj = scoreboard.getObjective("Money");
        if (moneyObj != null) {
            int current = scoreboard
                    .getOrCreatePlayerScore(ScoreHolder.forNameOnly(player.getScoreboardName()), moneyObj).get();
            scoreboard.getOrCreatePlayerScore(ScoreHolder.forNameOnly(player.getScoreboardName()), moneyObj)
                    .set(current + amount);
        }
    }

    /**
     * Tries to find if the required items are available in nearby containers.
     * Returns true if all items can be extracted.
     */
    public static boolean hasStock(Level level, BlockPos computerPos, List<ItemStack> cart) {
        // Find adjacent item handlers (chests/barrels next to the computer)
        List<IItemHandler> inventorySources = getAdjacentInventories(level, computerPos);

        // Clone the cart to track what we still need to find
        List<ItemStack> remainingToFind = new ArrayList<>();
        for (ItemStack stack : cart) {
            remainingToFind.add(stack.copy());
        }

        // Check if we can fulfill all remaining items
        for (ItemStack required : remainingToFind) {
            int requiredCount = required.getCount();

            for (IItemHandler handler : inventorySources) {
                for (int slot = 0; slot < handler.getSlots(); slot++) {
                    ItemStack inSlot = handler.getStackInSlot(slot);
                    if (ItemStack.isSameItemSameComponents(inSlot, required)) {
                        int amountMatched = Math.min(inSlot.getCount(), requiredCount);
                        requiredCount -= amountMatched;
                        if (requiredCount <= 0)
                            break;
                    }
                }
                if (requiredCount <= 0)
                    break;
            }
            if (requiredCount > 0)
                return false; // Stock missing
        }
        return true;
    }

    /**
     * Extracts the required items from nearby containers. Call hasStock first
     * before calling this!
     */
    public static void takeStock(Level level, BlockPos computerPos, List<ItemStack> cart) {
        List<IItemHandler> inventorySources = getAdjacentInventories(level, computerPos);

        for (ItemStack required : cart) {
            int requiredCount = required.getCount();

            for (IItemHandler handler : inventorySources) {
                for (int slot = 0; slot < handler.getSlots(); slot++) {
                    ItemStack inSlot = handler.getStackInSlot(slot);
                    if (ItemStack.isSameItemSameComponents(inSlot, required)) {
                        int toExtract = Math.min(inSlot.getCount(), requiredCount);
                        handler.extractItem(slot, toExtract, false); // false = actually extract
                        requiredCount -= toExtract;
                        if (requiredCount <= 0)
                            break;
                    }
                }
                if (requiredCount <= 0)
                    break;
            }
        }
    }

    private static List<IItemHandler> getAdjacentInventories(Level level, BlockPos pos) {
        List<IItemHandler> handlers = new ArrayList<>();
        // Check blocks in a small radius around the computer block for stock
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos p = pos.offset(x, y, z);
                    BlockEntity be = level.getBlockEntity(p);
                    if (be != null) {
                        IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, p, null);
                        if (handler != null) {
                            handlers.add(handler);
                        }
                    }
                }
            }
        }
        return handlers;
    }

    /**
     * Finalizes the transaction. Deducts money, removes stock, gives items to
     * player.
     * Returns true if successful.
     */
    public static boolean checkout(Player player, Level level, BlockPos computerPos, List<ItemStack> cart,
            int totalPrice) {
        if (getPlayerMoney(player) < totalPrice) {
            return false; // Not enough money
        }

        if (!hasStock(level, computerPos, cart)) {
            return false; // Missing stock
        }

        // Validated. Perform exchange.
        modifyPlayerMoney(player, -totalPrice);
        takeStock(level, computerPos, cart);

        for (ItemStack stack : cart) {
            if (!player.getInventory().add(stack.copy())) {
                player.drop(stack.copy(), false); // Drop if inventory full
            }
        }
        return true;
    }
}
