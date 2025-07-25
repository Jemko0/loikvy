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
		double i = 0;
		double roomSizeX = 0;
		double roomSizeY = 0;
		if (!world.isClientSide()) {
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				if (!world.canSeeSkyFromBelowWater(BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()))
						&& world.getMaxLocalRawBrightness(BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ())) < 6) {
					{
						LoikvyModVariables.PlayerVariables _vars = entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES);
						_vars.gPlayerHappiness = ClampNumberProcedure.execute(100, 0, entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHappiness - 0.005);
						_vars.syncPlayerVariables(entityiterator);
					}
				} else {
					{
						LoikvyModVariables.PlayerVariables _vars = entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES);
						_vars.gPlayerHappiness = ClampNumberProcedure.execute(100, 0, entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerHappiness + 0.001);
						_vars.syncPlayerVariables(entityiterator);
					}
				}
				i = 0;
				for (int index0 = 0; index0 < 8; index0++) {
					if (!((world.getBlockState(BlockPos.containing(entityiterator.getX() + i, entityiterator.getY() + 1, entityiterator.getZ()))).getBlock() == Blocks.AIR)) {
						break;
					}
					roomSizeX = i;
					i = i + 1;
				}
				i = 0;
				for (int index1 = 0; index1 < 8; index1++) {
					if (!((world.getBlockState(BlockPos.containing(entityiterator.getX(), entityiterator.getY() + 1, entityiterator.getZ() + i))).getBlock() == Blocks.AIR)) {
						break;
					}
					roomSizeY = i;
					i = i + 1;
				}
				if (entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsClaustrophobic) {
					if (roomSizeX < 5 && roomSizeY < 5) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 60, 0));
					}
				}
				if (entityiterator.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsAgoraphobic) {
					if (roomSizeX > 5 || roomSizeY > 5) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.PANIC, 60, 0));
					}
				}
			}
			LoikvyMod.queueServerWork(20, () -> {
				PlayerOffTickProcedure.execute(world);
			});
		}
	}
}