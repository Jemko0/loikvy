package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class CheesePizzaItem extends Item {
	public CheesePizzaItem() {
		super(new Item.Properties().stacksTo(1).food((new FoodProperties.Builder()).nutrition(12).saturationModifier(0f).build()));
	}
}