package net.mcreator.loikvy.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.loikvy.init.LoikvyModFluids;

public class GasolineItem extends BucketItem {
	public GasolineItem() {
		super(LoikvyModFluids.GASOLINE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)

		);
	}
}