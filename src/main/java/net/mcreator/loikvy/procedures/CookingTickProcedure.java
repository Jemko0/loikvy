package net.mcreator.loikvy.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.init.LoikvyModItems;

public class CookingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, boolean needsOil, double cookSpeedMultiplier, double maxSlot, double minSlot) {
		ItemStack item = ItemStack.EMPTY;
		double i = 0;
		boolean hasHeat = false;
		i = minSlot;
		for (int index0 = 0; index0 < (int) maxSlot; index0++) {
			item = (itemFromBlockInventory(world, BlockPos.containing(x, y, z), (int) i).copy()).copy();
			if (item.getItem() == LoikvyModItems.BURNT_FOOD.get()) {
				if (Mth.nextDouble(RandomSource.create(), 0, 1) > 0.8) {
					if (world instanceof ServerLevel projectileLevel) {
						Projectile _entityToSpawn = initProjectileProperties(new SmallFireball(EntityType.SMALL_FIREBALL, projectileLevel), null,
								new Vec3((Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1))));
						_entityToSpawn.setPos(x, y, z);
						_entityToSpawn.shoot((Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1)), 1, 1);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			}
			if (item.is(ItemTags.create(ResourceLocation.parse("loikvy:explode_on_cook")))) {
				if (world instanceof Level _level && !_level.isClientSide())
					_level.explode(null, x, y, z, 8, Level.ExplosionInteraction.BLOCK);
			}
			if (item.is(ItemTags.create(ResourceLocation.parse("loikvy:non_cookable"))) || item.getItem() == Blocks.AIR.asItem()) {
				i = i + 1;
				continue;
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 1, 0, 0.2, 0, 0.01);
			if (item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cooking_progress") >= 100) {
				if (world instanceof Level _level17 && _level17.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(item), _level17).isPresent()) {
					item = (world instanceof Level _lvlSmeltResult
							? _lvlSmeltResult.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(item), _lvlSmeltResult).map(recipe -> recipe.value().getResultItem(_lvlSmeltResult.registryAccess()).copy())
									.orElse(ItemStack.EMPTY)
							: ItemStack.EMPTY).copy();
					{
						final String _tagName = "cooking_progress";
						final double _tagValue = 100;
						CustomData.update(DataComponents.CUSTOM_DATA, item, tag -> tag.putDouble(_tagName, _tagValue));
					}
				}
				{
					final String _tagName = "burning";
					final double _tagValue = (item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("burning") + 1);
					CustomData.update(DataComponents.CUSTOM_DATA, item, tag -> tag.putDouble(_tagName, _tagValue));
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.SMALL_FLAME, x, y, z, 2, 1, 0.3, 1, 0.01);
				if (Mth.nextDouble(RandomSource.create(), 0, 1) > 0.8) {
					if (item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("burning") > 10) {
						if (world instanceof ServerLevel projectileLevel) {
							Projectile _entityToSpawn = initProjectileProperties(new SmallFireball(EntityType.SMALL_FIREBALL, projectileLevel), null,
									new Vec3((Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1))));
							_entityToSpawn.setPos(x, y, z);
							_entityToSpawn.shoot((Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1)), (Mth.nextDouble(RandomSource.create(), 0, 1)), 1, 1);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				}
				if (item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("burning") >= 100) {
					item = new ItemStack(LoikvyModItems.BURNT_FOOD.get()).copy();
				}
			} else {
				{
					final String _tagName = "cooking_progress";
					final double _tagValue = (item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cooking_progress") + 0.2 * cookSpeedMultiplier);
					CustomData.update(DataComponents.CUSTOM_DATA, item, tag -> tag.putDouble(_tagName, _tagValue));
				}
			}
			if (needsOil) {
				if (world instanceof ILevelExtension _ext && world instanceof ServerLevel _serverLevel
						&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
					int _slotid = 0;
					ItemStack _stk = _itemHandlerModifiable.getStackInSlot(_slotid).copy();
					_stk.hurtAndBreak(1, _serverLevel, null, _stkprov -> {
					});
					_itemHandlerModifiable.setStackInSlot(_slotid, _stk);
				}
			}
			if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
				ItemStack _setstack = item.copy();
				_setstack.setCount(1);
				_itemHandlerModifiable.setStackInSlot((int) i, _setstack);
			}
			i = i + 1;
		}
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
			if (itemHandler != null)
				return itemHandler.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
	}

	private static Projectile initProjectileProperties(Projectile entityToSpawn, Entity shooter, Vec3 acceleration) {
		entityToSpawn.setOwner(shooter);
		if (!Vec3.ZERO.equals(acceleration)) {
			entityToSpawn.setDeltaMovement(acceleration);
			entityToSpawn.hasImpulse = true;
		}
		return entityToSpawn;
	}
}