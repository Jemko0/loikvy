package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class PepperoniSliceItem extends Item {
	public PepperoniSliceItem() {
		super(new Item.Properties().stacksTo(16).food((new FoodProperties.Builder()).nutrition(1).saturationModifier(0f).build()));
	}
}