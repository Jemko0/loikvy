package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class PizzaSliceItem extends Item {
	public PizzaSliceItem() {
		super(new Item.Properties().stacksTo(8).food((new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3f).build()));
	}
}