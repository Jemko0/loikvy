package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class PepperoniChunkItem extends Item {
	public PepperoniChunkItem() {
		super(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationModifier(0f).alwaysEdible().build()));
	}
}