package net.mcreator.loikvy.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

public class LettuceCropBlock extends Block {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);

    public LettuceCropBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.GRASS).strength(0f, 0f).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false).randomTicks());
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(0, 0, 0, 16, 4, 16);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STAGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(STAGE, 0);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide) {
            int currentStage = state.getValue(STAGE);
            
            // Only grow if not at max stage
            if (currentStage < 3) {
                // Check if there's enough light (at least level 9)
                if (level.getRawBrightness(pos, 0) >= 9) {
                    // 25% chance to grow each random tick
                    if (random.nextInt(4) == 0) {
                        level.setBlock(pos, state.setValue(STAGE, currentStage + 1), 2);
                    }
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(STAGE) < 3;
    }
}