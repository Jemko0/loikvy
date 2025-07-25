package net.mcreator.loikvy.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class AABatteryItem extends Item {
	public AABatteryItem() {
		super(new Item.Properties().durability(1000).rarity(Rarity.RARE));
	}
}