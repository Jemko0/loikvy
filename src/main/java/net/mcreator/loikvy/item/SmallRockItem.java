package net.mcreator.loikvy.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public class SmallRockItem extends Item {
	public SmallRockItem() {
		super(new Item.Properties());
	}

	@Override
	public float getDestroySpeed(ItemStack itemstack, BlockState state) {
		return 2f;
	}
}