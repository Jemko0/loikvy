package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class LettuceItem extends Item {
	public LettuceItem() {
		super(new Item.Properties().stacksTo(25).food((new FoodProperties.Builder()).nutrition(1).saturationModifier(-1f).build()));
	}
}