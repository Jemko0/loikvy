package net.mcreator.loikvy.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;

public class CheeseWheelItem extends Item {
	public CheeseWheelItem() {
		super(new Item.Properties().stacksTo(1).food((new FoodProperties.Builder()).nutrition(10).saturationModifier(2f).build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity livingEntity) {
		return 300;
	}
}