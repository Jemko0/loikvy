package net.mcreator.loikvy.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.BlockLightEngine;
import net.minecraft.world.level.lighting.BlockLightSectionStorage;
import net.minecraft.world.level.lighting.LightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockLightEngine.class)
public abstract class BlockLightEngineMixin
        extends LightEngine<BlockLightSectionStorage.BlockDataLayerStorageMap, BlockLightSectionStorage> {

    @Shadow
    private BlockPos.MutableBlockPos mutablePos;

    private BlockLightEngineMixin() {
        super(null, null);
    }

    private int getEffectiveOpacity(BlockState state, BlockPos.MutableBlockPos pos, long packedPos) {
        int opacity = this.getOpacity(state, pos);
        int bx = BlockPos.getX(packedPos);
        int by = BlockPos.getY(packedPos);
        int bz = BlockPos.getZ(packedPos);
        if (opacity <= 1 && ((bx + by + bz) & 1) == 0) {
            return 0;
        }
        return opacity;
    }

    /**
     * @author Loikvy
     * @reason Double block light reach
     */
    @Overwrite
    protected void propagateIncrease(long p_285500_, long p_285410_, int p_285492_) {
        BlockState blockstate = null;

        for (Direction direction : PROPAGATION_DIRECTIONS) {
            if (QueueEntry.shouldPropagateInDirection(p_285410_, direction)) {
                long i = BlockPos.offset(p_285500_, direction);
                if (((BlockLightSectionStorage) this.storage).storingLightForSection(SectionPos.blockToSection(i))) {
                    int j = ((BlockLightSectionStorage) this.storage).getStoredLevel(i);
                    int k = p_285492_ - 1;
                    if (k > j) {
                        this.mutablePos.set(i);
                        BlockState blockstate1 = this.getState(this.mutablePos);
                        int l = p_285492_ - this.getEffectiveOpacity(blockstate1, this.mutablePos, i);
                        l = Math.min(l, 15);

                        if (l > j) {
                            if (blockstate == null) {
                                blockstate = QueueEntry.isFromEmptyShape(p_285410_)
                                        ? Blocks.AIR.defaultBlockState()
                                        : this.getState(this.mutablePos.set(p_285500_));
                            }

                            if (!this.shapeOccludes(p_285500_, blockstate, i, blockstate1, direction)) {
                                ((BlockLightSectionStorage) this.storage).setStoredLevel(i, l);
                                if (l > 1) {
                                    this.enqueueIncrease(i, QueueEntry.increaseSkipOneDirection(l, isEmptyShape(blockstate1), direction.getOpposite()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @author Loikvy
     * @reason Match decrease propagation to doubled light range
     */
    @Overwrite
    protected void propagateDecrease(long p_285435_, long p_285230_) {
        int i = QueueEntry.getFromLevel(p_285230_);

        for (Direction direction : PROPAGATION_DIRECTIONS) {
            if (QueueEntry.shouldPropagateInDirection(p_285230_, direction)) {
                long j = BlockPos.offset(p_285435_, direction);
                if (((BlockLightSectionStorage) this.storage).storingLightForSection(SectionPos.blockToSection(j))) {
                    int k = ((BlockLightSectionStorage) this.storage).getStoredLevel(j);
                    if (k != 0) {
                        // Use effective opacity to determine how far decrease should reach
                        this.mutablePos.set(j);
                        BlockState blockstate = this.getState(this.mutablePos);
                        int effectiveOpacity = this.getEffectiveOpacity(blockstate, this.mutablePos, j);
                        int expectedMax = i - effectiveOpacity;
                        expectedMax = Math.min(expectedMax, 15);

                        if (k <= expectedMax) {
                            int l = this.getEmission(j, blockstate);
                            ((BlockLightSectionStorage) this.storage).setStoredLevel(j, 0);
                            if (l < k) {
                                this.enqueueDecrease(j, QueueEntry.decreaseSkipOneDirection(k, direction.getOpposite()));
                            }
                            if (l > 0) {
                                this.enqueueIncrease(j, QueueEntry.increaseLightFromEmission(l, isEmptyShape(blockstate)));
                            }
                        } else {
                            this.enqueueIncrease(j, QueueEntry.increaseOnlyOneDirection(k, false, direction.getOpposite()));
                        }
                    }
                }
            }
        }
    }

    @Shadow
    protected abstract int getEmission(long pos, BlockState state);
}