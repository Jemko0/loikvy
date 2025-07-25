package net.mcreator.loikvy.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.loikvy.init.LoikvyModMenus;

public class HasOilProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu ? _menu.getSlots().get(0).getItem() : ItemStack.EMPTY)
				.is(ItemTags.create(ResourceLocation.parse("loikvy:cooking_oil"))));
	}
}