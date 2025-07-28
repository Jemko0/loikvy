package net.mcreator.loikvy.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.init.LoikvyModMenus;

public class ReceiptAddElementProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((executeCommandGetResult(world, new Vec3(x, y, z),
					("data modify storage minecraft:temp_receipt temp append value \"\u00A7" + ""
							+ ((entity instanceof Player _entity0 && _entity0.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "color", "") : "")
							+ ((entity instanceof Player _entity1 && _entity1.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(0, "content", "") : "") + "\"")))),
					false);
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