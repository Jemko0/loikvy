package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.network.LoikvyModVariables;
import net.mcreator.loikvy.init.LoikvyModMobEffects;
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
		if ((BuiltInRegistries.BLOCK.getKey((world.getBlockState(BlockPos.containing(x, y, z))).getBlock()).toString()).contains("glass")) {
			if (!((entity instanceof LivingEntity _entUseItem2 ? _entUseItem2.getUseItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("minecraft:enchantable/mining")))
					|| (entity instanceof LivingEntity _entUseItem4 ? _entUseItem4.getUseItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("minecraft:enchantable/durability")))
					|| (entity instanceof LivingEntity _entUseItem6 ? _entUseItem6.getUseItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("minecraft:enchantable/weapon"))))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(LoikvyModMobEffects.BLEEDING, 300, 0, false, true));
			}
		}
		if ((BuiltInRegistries.ITEM.getKey((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()).toString()).contains("hoe")) {
			LoikvyMod.LOGGER.info(entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock);
			if (BuiltInRegistries.BLOCK.get(ResourceLocation.parse((entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock).toLowerCase(java.util.Locale.ENGLISH))) == Blocks.GRASS_BLOCK
					|| BuiltInRegistries.BLOCK.get(ResourceLocation.parse((entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerCurrentBreakBlock).toLowerCase(java.util.Locale.ENGLISH))) == Blocks.DIRT) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				world.setBlock(BlockPos.containing(x, y, z), Blocks.FARMLAND.defaultBlockState(), 3);
			}
		}
	}
}