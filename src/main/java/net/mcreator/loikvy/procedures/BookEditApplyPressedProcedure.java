package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DataComponents;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.loikvy.init.LoikvyModMenus;

public class BookEditApplyPressedProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        ItemStack item = ItemStack.EMPTY;
        String newTitle = "";
        String newAuthor = "";
        
        item = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu0 ? _menu0.getSlots().get(0).getItem() : ItemStack.EMPTY).copy();
        newTitle = (entity instanceof Player _entity1 && _entity1.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(0, "title", "") : "";
        newAuthor = (entity instanceof Player _entity2 && _entity2.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu2) ? _menu2.getMenuState(0, "author", "") : "";
        
        if (item.getItem() == Items.WRITTEN_BOOK) {
            WrittenBookContent bookContent = item.get(DataComponents.WRITTEN_BOOK_CONTENT);
            
            if (bookContent != null)
            {
                String finalTitle = !newTitle.isEmpty() ? newTitle : bookContent.title().getString();
                String finalAuthor = !newAuthor.isEmpty() ? newAuthor : bookContent.author();
                
                WrittenBookContent newContent = new WrittenBookContent(
                    finalAuthor,
                    bookContent.generation(),
                    Component.literal(finalTitle),
                    bookContent.pages(),
                    bookContent.resolved()
                );
               
                item.set(DataComponents.WRITTEN_BOOK_CONTENT, newContent);
                
                if (entity instanceof Player _player && _player.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu) {
                    _menu.getSlots().get(0).set(item);
                }
            }
        }
    }
}