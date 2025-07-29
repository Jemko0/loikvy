package net.mcreator.loikvy.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public class EmptyCanItem extends Item {
	public EmptyCanItem() {
		super(new Item.Properties().stacksTo(16));
	}

	@Override
	public float getDestroySpeed(ItemStack itemstack, BlockState state) {
		return 0.7f;
	}
}