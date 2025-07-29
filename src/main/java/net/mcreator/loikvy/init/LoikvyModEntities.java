/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import net.mcreator.loikvy.entity.WheelchairEntity;
import net.mcreator.loikvy.entity.ShopCashierEntity;
import net.mcreator.loikvy.entity.GroundItemEntity;
import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, LoikvyMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<ShopCashierEntity>> SHOP_CASHIER = register("shop_cashier",
			EntityType.Builder.<ShopCashierEntity>of(ShopCashierEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.ridingOffset(-0.6f).sized(0.6f, 1.8f));
	public static final DeferredHolder<EntityType<?>, EntityType<WheelchairEntity>> WHEELCHAIR = register("wheelchair",
			EntityType.Builder.<WheelchairEntity>of(WheelchairEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final DeferredHolder<EntityType<?>, EntityType<GroundItemEntity>> GROUND_ITEM = register("ground_item",
			EntityType.Builder.<GroundItemEntity>of(GroundItemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.ridingOffset(-0.6f).sized(0.6f, 1f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerEntity(Capabilities.ItemHandler.ENTITY, SHOP_CASHIER.get(), (living, context) -> living.getCombinedInventory());
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		ShopCashierEntity.init(event);
		WheelchairEntity.init(event);
		GroundItemEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SHOP_CASHIER.get(), ShopCashierEntity.createAttributes().build());
		event.put(WHEELCHAIR.get(), WheelchairEntity.createAttributes().build());
		event.put(GROUND_ITEM.get(), GroundItemEntity.createAttributes().build());
	}
}