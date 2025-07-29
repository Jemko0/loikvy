package net.mcreator.loikvy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class CookedNoodlesItem extends Item {
	public CookedNoodlesItem() {
		super(new Item.Properties().food((new FoodProperties.Builder()).nutrition(8).saturationModifier(2f).alwaysEdible().build()));
	}
}