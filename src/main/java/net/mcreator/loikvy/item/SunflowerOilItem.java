package net.mcreator.loikvy.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.loikvy.procedures.SunflowerOilPlayerFinishesUsingItemProcedure;

public class SunflowerOilItem extends Item {
	public SunflowerOilItem() {
		super(new Item.Properties().durability(10000).food((new FoodProperties.Builder()).nutrition(4).saturationModifier(-1f).alwaysEdible().build()));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.DRINK;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		SunflowerOilPlayerFinishesUsingItemProcedure.execute(entity);
		return retval;
	}
}