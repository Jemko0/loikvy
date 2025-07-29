package net.mcreator.loikvy.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.loikvy.procedures.BurntFoodPlayerFinishesUsingItemProcedure;

public class UncookedNoodlesItem extends Item {
	public UncookedNoodlesItem() {
		super(new Item.Properties().stacksTo(8).food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0f).alwaysEdible().build()));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		BurntFoodPlayerFinishesUsingItemProcedure.execute(world, entity);
		return retval;
	}
}