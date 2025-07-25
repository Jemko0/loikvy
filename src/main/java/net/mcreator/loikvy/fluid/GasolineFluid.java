package net.mcreator.loikvy.fluid;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.loikvy.init.LoikvyModItems;
import net.mcreator.loikvy.init.LoikvyModFluids;
import net.mcreator.loikvy.init.LoikvyModFluidTypes;
import net.mcreator.loikvy.init.LoikvyModBlocks;

public abstract class GasolineFluid extends BaseFlowingFluid {
	public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(() -> LoikvyModFluidTypes.GASOLINE_TYPE.get(), () -> LoikvyModFluids.GASOLINE.get(), () -> LoikvyModFluids.FLOWING_GASOLINE.get())
			.explosionResistance(0f).bucket(() -> LoikvyModItems.GASOLINE_BUCKET.get()).block(() -> (LiquidBlock) LoikvyModBlocks.GASOLINE.get());

	private GasolineFluid() {
		super(PROPERTIES);
	}

	@Override
	public ParticleOptions getDripParticle() {
		return ParticleTypes.DRIPPING_WATER;
	}

	public static class Source extends GasolineFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends GasolineFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}