package net.mcreator.loikvy.command;

import org.checkerframework.checker.units.qual.s;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.Commands;

import net.mcreator.loikvy.procedures.CreateIDProcProcedure;

import com.mojang.brigadier.arguments.StringArgumentType;

@EventBusSubscriber
public class CreateIDCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("createid").requires(s -> s.hasPermission(2)).then(Commands.argument("firstname", StringArgumentType.string()).then(Commands.argument("lastname", StringArgumentType.string())
				.then(Commands.argument("dob", StringArgumentType.string()).then(Commands.argument("idnum", StringArgumentType.string()).then(Commands.argument("expire", StringArgumentType.string()).executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					double x = arguments.getSource().getPosition().x();
					double y = arguments.getSource().getPosition().y();
					double z = arguments.getSource().getPosition().z();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					Direction direction = Direction.DOWN;
					if (entity != null)
						direction = entity.getDirection();

					CreateIDProcProcedure.execute(arguments, entity);
					return 0;
				})))))));
	}

}