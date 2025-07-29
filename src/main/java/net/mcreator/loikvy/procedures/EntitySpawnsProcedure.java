package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.entity.GroundItemEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class EntitySpawnsProcedure {
	@SubscribeEvent
	public static void onEntitySpawned(EntityJoinLevelEvent event) {
		execute(event, event.getLevel(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity itemdisplay = null;
		if (!world.isClientSide()) {
			if (entity instanceof GroundItemEntity) {
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("enti spanw :p"), false);
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList()
							.broadcastSystemMessage(Component.literal((executeCommandGetResult(world, new Vec3(x, y, z),
									("summon item_display ~ ~ ~ " + "{item_display:\"fixed\",transformation:{left_rotation:[0f,0f,0f,1f],right_rotation:[0f,0f,90f,0f],translation:[0f,0f,0f],scale:[1f,1f,1f]},item:{id:\""
											+ (entity instanceof GroundItemEntity _datEntS ? _datEntS.getEntityData().get(GroundItemEntity.DATA_item) : "") + "\",count:1}}")))),
									false);
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