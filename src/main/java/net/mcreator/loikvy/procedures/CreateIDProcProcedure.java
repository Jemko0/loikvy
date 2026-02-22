package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.loikvy.init.LoikvyModItems;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

public class CreateIDProcProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		ItemStack stack = ItemStack.EMPTY;
		stack = new ItemStack(LoikvyModItems.ID_CARD.get()).copy();
		{
			final String _tagName = "owner_first_name";
			final String _tagValue = (StringArgumentType.getString(arguments, "firstname"));
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(_tagName, _tagValue));
		}
		{
			final String _tagName = "owner_last_name";
			final String _tagValue = (StringArgumentType.getString(arguments, "lastname"));
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(_tagName, _tagValue));
		}
		{
			final String _tagName = "owner_dob";
			final String _tagValue = (StringArgumentType.getString(arguments, "dob"));
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(_tagName, _tagValue));
		}
		{
			final String _tagName = "id_num";
			final String _tagValue = (StringArgumentType.getString(arguments, "idnum"));
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(_tagName, _tagValue));
		}
		{
			final String _tagName = "expiry";
			final String _tagValue = (StringArgumentType.getString(arguments, "expire"));
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(_tagName, _tagValue));
		}
		if (entity instanceof Player _player) {
			ItemStack _setstack = stack.copy();
			_setstack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
		}
	}
}