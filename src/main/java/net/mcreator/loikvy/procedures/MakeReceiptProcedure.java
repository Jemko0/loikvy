package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.init.LoikvyModMenus;
import net.mcreator.loikvy.LoikvyMod;

public class MakeReceiptProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String cmdout = "";
		String storename = "";
		String date = "";
		String lore = "";
		ItemStack newReceipt = ItemStack.EMPTY;
		double indexof = 0;
		double lastQuotes = 0;
		storename = (entity instanceof Player _entity && _entity.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu) ? _menu.getMenuState(0, "store", "") : "";
		date = GetCalendarDaysProcedure.execute();
		if (!world.isClientSide()) {
			cmdout = executeCommandGetResult(world, new Vec3(x, y, z), "data get storage minecraft:temp_receipt temp");
			indexof = cmdout.indexOf("[", 0);
			lore = cmdout.substring((int) indexof);
			lore = lore.replace("[", "");
			lore = lore.replace("]", "");
			lore = lore.replace(", ", ";");
			lore = lore.replace("\"", "");
			LoikvyMod.LOGGER.info(lore);
			newReceipt = new ItemStack(Items.PAPER).copy();
			newReceipt.set(DataComponents.CUSTOM_NAME, Component.literal((storename + " receipt " + date)));
			{
				final String _tagName = "receipt";
				final String _tagValue = lore;
				CustomData.update(DataComponents.CUSTOM_DATA, newReceipt, tag -> tag.putString(_tagName, _tagValue));
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = newReceipt.copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
	}

	private static String executeCommandGetResult(LevelAccessor world, Vec3 pos, String command) {
		StringBuilder result = new StringBuilder();
		if (world instanceof ServerLevel level) {
			CommandSource dataConsumer = new CommandSource() {
				@Override
				public void sendSystemMessage(Component message) {
					result.append(message.getString());
				}

				@Override
				public boolean acceptsSuccess() {
					return true;
				}

				@Override
				public boolean acceptsFailure() {
					return true;
				}

				@Override
				public boolean shouldInformAdmins() {
					return false;
				}
			};
			level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(dataConsumer, pos, Vec2.ZERO, level, 4, "", Component.literal(""), level.getServer(), null), command);
		}
		return result.toString();
	}
}