package net.mcreator.loikvy.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;

public class CheeseSliceItem extends Item {
	public CheeseSliceItem() {
		super(new Item.Properties().stacksTo(8).food((new FoodProperties.Builder()).nutrition(2).saturationModifier(0f).build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity livingEntity) {
		return 37;
	}
}