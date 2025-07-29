package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;
import net.mcreator.loikvy.init.LoikvyModItems;
import net.mcreator.loikvy.LoikvyMod;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerEventsProcedure {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getPlayer());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player)
			_player.causeFoodExhaustion(1);
		if ((BuiltInRegistries.BLOCK.getKey((world.getBlockState(BlockPos.containing(x, y, z))).getBlock()).toString()).contains("glass")) {
			if (!((entity instanceof LivingEntity _entUseItem3 ? _entUseItem3.getUseItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("minecraft:enchantable/mining")))
					|| (entity instanceof LivingEntity _entUseItem5 ? _entUseItem5.getUseItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("minecraft:enchantable/durability")))
					|| (entity instanceof LivingEntity _entUseItem7 ? _entUseItem7.getUseItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("minecraft:enchantable/weapon"))))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.BLEEDING, 300, 0, false, true));
			}
		}
		if ((BuiltInRegistries.ITEM.getKey((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()).toString()).contains("hoe")) {
			LoikvyMod.LOGGER.info(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock);
			if (BuiltInRegistries.BLOCK.get(ResourceLocation.parse((entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock).toLowerCase(java.util.Locale.ENGLISH))).defaultBlockState()
					.is(BlockTags.create(ResourceLocation.parse("loikvy:tillable")))) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				world.setBlock(BlockPos.containing(x, y, z), Blocks.FARMLAND.defaultBlockState(), 3);
			}
		}
		if (BuiltInRegistries.BLOCK.get(ResourceLocation.parse((entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock).toLowerCase(java.util.Locale.ENGLISH))).defaultBlockState()
				.is(BlockTags.create(ResourceLocation.parse("loikvy:planks")))) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.CHISEL.get()) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				world.setBlock(BlockPos.containing(x, y, z), Blocks.CRAFTING_TABLE.defaultBlockState(), 3);
			}
		}
		if (BuiltInRegistries.BLOCK.get(ResourceLocation.parse((entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock).toLowerCase(java.util.Locale.ENGLISH))) == Blocks.STONE) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == LoikvyModItems.SMALL_ROCK.get()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(LoikvyModItems.SHARP_ROCK.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.anvil.land")), SoundSource.BLOCKS, 1, (float) 1.5);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.anvil.land")), SoundSource.BLOCKS, 1, (float) 1.5, false);
					}
				}
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.BLEEDING, 100, 0, false, true));
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(LoikvyModItems.SMALL_ROCK.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.anvil.land")), SoundSource.BLOCKS, 1, (float) 1.5);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.anvil.land")), SoundSource.BLOCKS, 1, (float) 1.5, false);
					}
				}
			}
		}
	}
}