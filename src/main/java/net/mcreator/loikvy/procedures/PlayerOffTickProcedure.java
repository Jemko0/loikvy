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
				BlockPos playerPos = entityiterator.blockPosition();
				BlockPos abovePlayer = playerPos.above();

				LoikvyModVariables.PlayerVariables playerVars = entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES);
				boolean needsSync = false;

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

				if (playerVars.gPlayerIsClaustrophobic || playerVars.gPlayerIsAgoraphobic) {
					int[] distances = new int[4];
					int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // +X, -X, +Z, -Z

					for (int i = 0; i < 4; i++) {
						int dx = directions[i][0];
						int dz = directions[i][1];

						distances[i] = 8;

						for (int distance = 1; distance <= 7; distance++) {
							BlockPos checkPos = playerPos.offset(dx * distance, 0, dz * distance);

							if (!world.getBlockState(checkPos).isAir()) {
								distances[i] = distance;
								break;
							}
						}
					}

					int smallDistances = 0;
					int largeDistances = 0;

					for (int dist : distances) {
						if (dist <= 3) smallDistances++;
						if (dist >= 6) largeDistances++;
					}

					if (playerVars.gPlayerIsClaustrophobic && smallDistances >= 3) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 60, 0, false, false));
					}

					if (playerVars.gPlayerIsAgoraphobic && largeDistances >= 3) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 60, 0, false, false));
					}
				}

				//nly sync if something actually changed
				if (needsSync) {
					playerVars.syncPlayerVariables(entityiterator);
				}
			}

			LoikvyMod.queueServerWork(40, () -> {
				PlayerOffTickProcedure.execute(world);
			});
		}
	}
}