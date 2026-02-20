package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

import net.mcreator.loikvy.init.LoikvyModDataAttachments;

import javax.annotation.Nullable;

@EventBusSubscriber
public class OnFoodEatenProcedure {
	@SubscribeEvent
	public static void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity(), event.getItem());
		}
	}

	public static void execute(Entity entity, ItemStack itemstack) {
		execute(null, entity, itemstack);
	}

	private static void execute(@Nullable Event event, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double foodSpoilageLevel = 0;
		double foodSpoilageFactor = 0;
		double foodFoodValue = 0;
		if ((itemstack.has(DataComponents.FOOD) ? itemstack.getFoodProperties(null).nutrition() : 0) > 0) {
			foodSpoilageLevel = itemstack.get(LoikvyModDataAttachments.SPOILAGE.get());;
			foodSpoilageFactor = foodSpoilageLevel / 100;
			foodFoodValue = itemstack.has(DataComponents.FOOD) ? itemstack.getFoodProperties(null).nutrition() : 0;
			if (entity instanceof Player _player)
				_player.getFoodData().setFoodLevel((int) ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) - foodFoodValue * foodSpoilageFactor));
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((" / spoilageFactor: " + foodSpoilageFactor + " / foodValue: " + foodFoodValue + " / givenFoodValue: " + foodFoodValue * (1 - foodSpoilageFactor))), false);
		}
	}
}