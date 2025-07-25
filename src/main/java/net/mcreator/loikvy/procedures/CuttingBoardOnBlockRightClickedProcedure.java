package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.loikvy.init.LoikvyModItems;

public class CuttingBoardOnBlockRightClickedProcedure {
	public static InteractionResult execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.CHEESE_WHEEL.get()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.KITCHEN_KNIFE.get()) {
			if (world instanceof ServerLevel _level) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).hurtAndBreak(4, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(LoikvyModItems.CHEESE_WHEEL.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(LoikvyModItems.CHEESE_SLICE.get()).copy();
				_setstack.setCount(8);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.TOMATO.get()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.KITCHEN_KNIFE.get()) {
			if (world instanceof ServerLevel _level) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).hurtAndBreak(2, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(LoikvyModItems.TOMATO.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(LoikvyModItems.TOMATO_SLICE.get()).copy();
				_setstack.setCount(4);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.LETTUCE.get()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.KITCHEN_KNIFE.get()) {
			if (world instanceof ServerLevel _level) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).hurtAndBreak(1, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(LoikvyModItems.LETTUCE.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(LoikvyModItems.LETTUCE_LEAF.get()).copy();
				_setstack.setCount(3);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.PEPPERONI_PIZZA.get()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.KITCHEN_KNIFE.get()) {
			if (world instanceof ServerLevel _level) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).hurtAndBreak(4, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(LoikvyModItems.PEPPERONI_PIZZA.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(LoikvyModItems.PIZZA_SLICE.get()).copy();
				_setstack.setCount(8);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.CHEESE_PIZZA.get()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.KITCHEN_KNIFE.get()) {
			if (world instanceof ServerLevel _level) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).hurtAndBreak(4, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(LoikvyModItems.CHEESE_PIZZA.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(LoikvyModItems.CHEESE_PIZZA_SLICE.get()).copy();
				_setstack.setCount(8);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.PEPPERONI_CHUNK.get()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.KITCHEN_KNIFE.get()) {
			if (world instanceof ServerLevel _level) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).hurtAndBreak(4, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(LoikvyModItems.PEPPERONI_CHUNK.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(LoikvyModItems.PEPPERONI_SLICE.get()).copy();
				_setstack.setCount(4);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
		return InteractionResult.CONSUME;
	}
}