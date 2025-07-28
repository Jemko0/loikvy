/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.loikvy.block.entity.WifiRouterBlockEntity;
import net.mcreator.loikvy.block.entity.StoveBlockEntity;
import net.mcreator.loikvy.block.entity.SmokeDetectorBlockEntity;
import net.mcreator.loikvy.block.entity.SinkBlockBlockEntity;
import net.mcreator.loikvy.block.entity.GamblingSlotsMachineBlockEntity;
import net.mcreator.loikvy.block.entity.GamblingBlackJackTableBlockEntity;
import net.mcreator.loikvy.block.entity.FridgeBlockEntity;
import net.mcreator.loikvy.block.entity.FreezerBlockEntity;
import net.mcreator.loikvy.block.entity.CookingPanBlockEntity;
import net.mcreator.loikvy.block.entity.CoalGeneratorBlockEntity;
import net.mcreator.loikvy.LoikvyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LoikvyMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FridgeBlockEntity>> FRIDGE = register("fridge", LoikvyModBlocks.FRIDGE, FridgeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FreezerBlockEntity>> FREEZER = register("freezer", LoikvyModBlocks.FREEZER, FreezerBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CookingPanBlockEntity>> COOKING_PAN = register("cooking_pan", LoikvyModBlocks.COOKING_PAN, CookingPanBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SmokeDetectorBlockEntity>> SMOKE_DETECTOR = register("smoke_detector", LoikvyModBlocks.SMOKE_DETECTOR, SmokeDetectorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = register("coal_generator", LoikvyModBlocks.COAL_GENERATOR, CoalGeneratorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StoveBlockEntity>> STOVE = register("stove", LoikvyModBlocks.STOVE, StoveBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SinkBlockBlockEntity>> SINK_BLOCK = register("sink_block", LoikvyModBlocks.SINK_BLOCK, SinkBlockBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GamblingSlotsMachineBlockEntity>> GAMBLING_SLOTS_MACHINE = register("gambling_slots_machine", LoikvyModBlocks.GAMBLING_SLOTS_MACHINE, GamblingSlotsMachineBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GamblingBlackJackTableBlockEntity>> GAMBLING_BLACK_JACK_TABLE = register("gambling_black_jack_table", LoikvyModBlocks.GAMBLING_BLACK_JACK_TABLE,
			GamblingBlackJackTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WifiRouterBlockEntity>> WIFI_ROUTER = register("wifi_router", LoikvyModBlocks.WIFI_ROUTER, WifiRouterBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FRIDGE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FREEZER.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, COOKING_PAN.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SMOKE_DETECTOR.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, COAL_GENERATOR.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, COAL_GENERATOR.get(), (blockEntity, side) -> blockEntity.getEnergyStorage());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, STOVE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SINK_BLOCK.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, GAMBLING_SLOTS_MACHINE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, GAMBLING_BLACK_JACK_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, WIFI_ROUTER.get(), SidedInvWrapper::new);
	}
}