package net.mcreator.loikvy.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.init.LoikvyModMenus;

public class KeyCreateButtonPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			Entity _ent = entity;
			if (!_ent.level().isClientSide() && _ent.getServer() != null) {
				_ent.getServer().getCommands().performPrefixedCommand(
						new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
								_ent.level().getServer(), _ent),
						("give @s loikvy:door_key[minecraft:custom_data={\"key_id\":\"" + "" + ((entity instanceof Player _entity0 && _entity0.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "key_id", "") : "")
								+ "\"},minecraft:lore=[\"KeyID\",\"" + ((entity instanceof Player _entity1 && _entity1.containerMenu instanceof LoikvyModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(0, "key_id", "") : "") + "\"]]"));
			}
		}
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}