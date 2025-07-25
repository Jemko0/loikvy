package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class TomatoItem extends Item {
	public TomatoItem() {
		super(new Item.Properties().stacksTo(15).food((new FoodProperties.Builder()).nutrition(2).saturationModifier(0f).build()));
	}
}