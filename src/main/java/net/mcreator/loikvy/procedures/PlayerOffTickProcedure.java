package net.mcreator.loikvy.procedures;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;
import net.mcreator.loikvy.LoikvyMod;

import javax.annotation.Nullable;

import java.util.ArrayList;

@EventBusSubscriber
public class PlayerOffTickProcedure {
	@SubscribeEvent
	public static void onWorldLoad(net.neoforged.neoforge.event.level.LevelEvent.Load event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		if (!world.isClientSide()) {
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				// Cache player position to avoid multiple calls
				BlockPos playerPos = entityiterator.blockPosition();
				BlockPos abovePlayer = playerPos.above();

				// Get current player variables ONCE
				LoikvyModVariables.PlayerVariables playerVars = entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES);
				boolean needsSync = false;

				// OPTIMIZATION 1: Only check sky/light if not in obvious indoor/outdoor situations
				// Check if there's a solid block directly above first (cheap check)
				boolean hasBlockAbove = !world.getBlockState(abovePlayer).isAir();

				if (hasBlockAbove) {
					// Likely indoors - skip expensive sky check
					if (world.getMaxLocalRawBrightness(playerPos) < 6) {
						double newHappiness = ClampNumberProcedure.execute(100, 0, playerVars.gPlayerHappiness - 0.005);
						if (playerVars.gPlayerHappiness != newHappiness) {
							playerVars.gPlayerHappiness = newHappiness;
							needsSync = true;
						}
					}
				} else {
					// No block above - likely can see sky, increase happiness
					double newHappiness = ClampNumberProcedure.execute(100, 0, playerVars.gPlayerHappiness + 0.001);
					if (playerVars.gPlayerHappiness != newHappiness) {
						playerVars.gPlayerHappiness = newHappiness;
						needsSync = true;
					}
				}

				// OPTIMIZATION 2: Only check room size if player has claustrophobia/agoraphobia
				if (playerVars.gPlayerIsClaustrophobic || playerVars.gPlayerIsAgoraphobic) {
					int roomSizeX = 0;
					int roomSizeY = 0;

					// Check X direction - but break early if we hit the threshold
					for (int i = 0; i < 8; i++) {
						if (!world.getBlockState(BlockPos.containing(entityiterator.getX() + i, entityiterator.getY() + 1, entityiterator.getZ())).isAir()) {
							break;
						}
						roomSizeX = i;
						// OPTIMIZATION: Early exit if we know room is big enough for agoraphobia
						if (playerVars.gPlayerIsAgoraphobic && i >= 5) {
							roomSizeX = i;
							break;
						}
					}

					// Check Z direction - same optimization
					for (int i = 0; i < 8; i++) {
						if (!world.getBlockState(BlockPos.containing(entityiterator.getX(), entityiterator.getY() + 1, entityiterator.getZ() + i)).isAir()) {
							break;
						}
						roomSizeY = i;
						if (playerVars.gPlayerIsAgoraphobic && i >= 5) {
							roomSizeY = i;
							break;
						}
					}

					// Apply effects based on room size
					if (playerVars.gPlayerIsClaustrophobic && roomSizeX < 5 && roomSizeY < 5) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 60, 0, false, false));
					}

					if (playerVars.gPlayerIsAgoraphobic && (roomSizeX > 5 || roomSizeY > 5)) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 60, 0, false, false));
					}
				}

				// OPTIMIZATION 3: Only sync if something actually changed
				if (needsSync) {
					playerVars.syncPlayerVariables(entityiterator);
				}
			}

			// OPTIMIZATION 4: Increase interval to 40 ticks (2 seconds) instead of 20
			// This halves the frequency and still feels responsive
			LoikvyMod.queueServerWork(40, () -> {
				PlayerOffTickProcedure.execute(world);
			});
		}
	}
}