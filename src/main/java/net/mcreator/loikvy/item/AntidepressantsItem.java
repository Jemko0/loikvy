package net.mcreator.loikvy.item;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.mcreator.loikvy.procedures.AntidepressantsPlayerFinishesUsingItemProcedure;

import java.util.List;

public class AntidepressantsItem extends Item {
	public AntidepressantsItem() {
		super(new Item.Properties().stacksTo(15).rarity(Rarity.EPIC).food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0f).alwaysEdible().build()));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity livingEntity) {
		return 5;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, list, flag);
		list.add(Component.translatable("item.loikvy.antidepressants.description_0"));
		list.add(Component.translatable("item.loikvy.antidepressants.description_1"));
		list.add(Component.translatable("item.loikvy.antidepressants.description_2"));
		list.add(Component.translatable("item.loikvy.antidepressants.description_3"));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		AntidepressantsPlayerFinishesUsingItemProcedure.execute(entity);
		return retval;
	}
}