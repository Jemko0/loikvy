package net.mcreator.loikvy.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class DoorKeyItem extends Item {
	public DoorKeyItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}
}