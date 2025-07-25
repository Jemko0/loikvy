/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;

import net.mcreator.loikvy.block.StoveBlock;
import net.mcreator.loikvy.block.SmokeDetectorBlock;
import net.mcreator.loikvy.block.SinkBlockBlock;
import net.mcreator.loikvy.block.LettuceCropBlock;
import net.mcreator.loikvy.block.GasolineBlock;
import net.mcreator.loikvy.block.GamblingSlotsMachineBlock;
import net.mcreator.loikvy.block.GamblingBlackJackTableBlock;
import net.mcreator.loikvy.block.FridgeBlock;
import net.mcreator.loikvy.block.FreezerBlock;
import net.mcreator.loikvy.block.CuttingBoardBlock;
import net.mcreator.loikvy.block.CookingPanBlock;
import net.mcreator.loikvy.block.CoalGeneratorBlock;
import net.mcreator.loikvy.block.BrighterTorchBlock;
import net.mcreator.loikvy.LoikvyMod;

public class LoikvyModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(LoikvyMod.MODID);
	public static final DeferredBlock<Block> GASOLINE = REGISTRY.register("gasoline", GasolineBlock::new);
	public static final DeferredBlock<Block> FRIDGE = REGISTRY.register("fridge", FridgeBlock::new);
	public static final DeferredBlock<Block> FREEZER = REGISTRY.register("freezer", FreezerBlock::new);
	public static final DeferredBlock<Block> CUTTING_BOARD = REGISTRY.register("cutting_board", CuttingBoardBlock::new);
	public static final DeferredBlock<Block> COOKING_PAN = REGISTRY.register("cooking_pan", CookingPanBlock::new);
	public static final DeferredBlock<Block> SMOKE_DETECTOR = REGISTRY.register("smoke_detector", SmokeDetectorBlock::new);
	public static final DeferredBlock<Block> COAL_GENERATOR = REGISTRY.register("coal_generator", CoalGeneratorBlock::new);
	public static final DeferredBlock<Block> STOVE = REGISTRY.register("stove", StoveBlock::new);
	public static final DeferredBlock<Block> LETTUCE_CROP = REGISTRY.register("lettuce_crop", LettuceCropBlock::new);
	public static final DeferredBlock<Block> SINK_BLOCK = REGISTRY.register("sink_block", SinkBlockBlock::new);
	public static final DeferredBlock<Block> GAMBLING_SLOTS_MACHINE = REGISTRY.register("gambling_slots_machine", GamblingSlotsMachineBlock::new);
	public static final DeferredBlock<Block> BRIGHTER_TORCH = REGISTRY.register("brighter_torch", BrighterTorchBlock::new);
	public static final DeferredBlock<Block> GAMBLING_BLACK_JACK_TABLE = REGISTRY.register("gambling_black_jack_table", GamblingBlackJackTableBlock::new);

	// Start of user code block custom blocks
	// End of user code block custom blocks
	@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class BlocksClientSideHandler {
		@SubscribeEvent
		public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
			CuttingBoardBlock.blockColorLoad(event);
		}

		@SubscribeEvent
		public static void itemColorLoad(RegisterColorHandlersEvent.Item event) {
			CuttingBoardBlock.itemColorLoad(event);
		}
	}
}