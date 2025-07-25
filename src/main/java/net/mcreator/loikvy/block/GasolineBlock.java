package net.mcreator.loikvy.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.procedures.GasolineBlockDestroyedByExplosionProcedure;
import net.mcreator.loikvy.init.LoikvyModFluids;

public class GasolineBlock extends LiquidBlock {
	public GasolineBlock() {
		super(LoikvyModFluids.GASOLINE.get(), BlockBehaviour.Properties.of().mapColor(MapColor.WATER).strength(0f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
	}

	@Override
	public void wasExploded(Level world, BlockPos pos, Explosion e) {
		super.wasExploded(world, pos, e);
		GasolineBlockDestroyedByExplosionProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}