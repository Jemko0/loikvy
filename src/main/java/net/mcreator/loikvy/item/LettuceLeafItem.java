package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class LettuceLeafItem extends Item {
	public LettuceLeafItem() {
		super(new Item.Properties().food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0f).build()));
	}
}