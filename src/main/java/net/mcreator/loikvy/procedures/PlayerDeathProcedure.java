package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.loikvy.network.LoikvyModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerDeathProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player) {
			if (entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerInDream == false) {
				if (!world.isClientSide()) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("playsound loikvy:loikvy_death ambient " + entity.getDisplayName().getString() + " ~ ~ ~ 10000 1 1"));
					if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandlerIter) {
						for (int _idx = 0; _idx < _modHandlerIter.getSlots(); _idx++) {
							ItemStack itemstackiterator = _modHandlerIter.getStackInSlot(_idx).copy();
							if (world instanceof ServerLevel _level) {
								ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
								entityToSpawn.setPickUpDelay(10);
								entityToSpawn.setUnlimitedLifetime();
								_level.addFreshEntity(entityToSpawn);
							}
						}
					}
				}
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth(20);
				if (entity instanceof ServerPlayer _player)
					_player.setGameMode(GameType.SPECTATOR);
				if (entity instanceof LivingEntity _entity)
					_entity.removeAllEffects();
				{
					LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
					_vars.gPlayerDeathTime = LoikvyModVariables.MapVariables.get(world).GlobalTicks;
					_vars.syncPlayerVariables(entity);
				}
				DeathTextProcedureProcedure.execute(entity);
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			} else {
				WakePlayerFromDreamProcedure.execute(entity);
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
		}
	}
}