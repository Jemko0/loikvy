package net.mcreator.loikvy.computer;

import net.mcreator.loikvy.LoikvyMod;
import net.mcreator.loikvy.computer.block.ComputerBlock;
import net.mcreator.loikvy.computer.block.entity.ComputerBlockEntity;
import net.mcreator.loikvy.computer.inventory.ComputerMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.entity.player.Inventory;

public class LoikvyComputerRegistry {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK,
                        LoikvyMod.MODID);
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM,
                        LoikvyMod.MODID);
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
                        .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LoikvyMod.MODID);
        public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
                        LoikvyMod.MODID);

        public static final DeferredHolder<Block, ComputerBlock> COMPUTER_BLOCK = BLOCKS.register("computer",
                        ComputerBlock::new);
        public static final DeferredHolder<Item, BlockItem> COMPUTER_ITEM = ITEMS.register("computer",
                        () -> new BlockItem(COMPUTER_BLOCK.get(), new Item.Properties()));
        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ComputerBlockEntity>> COMPUTER_BLOCK_ENTITY = BLOCK_ENTITIES
                        .register("computer", () -> BlockEntityType.Builder
                                        .of(ComputerBlockEntity::new, COMPUTER_BLOCK.get()).build(null));
        public static final DeferredHolder<MenuType<?>, MenuType<ComputerMenu>> COMPUTER_MENU = MENUS.register(
                        "computer_menu",
                        () -> IMenuTypeExtension.create((id, inv, buf) -> new ComputerMenu(id, inv, buf)));

        public static void register(IEventBus bus) {
                BLOCKS.register(bus);
                ITEMS.register(bus);
                BLOCK_ENTITIES.register(bus);
                MENUS.register(bus);
        }
}
