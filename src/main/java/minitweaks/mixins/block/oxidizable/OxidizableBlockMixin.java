package minitweaks.mixins.block.oxidizable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.OxidizableBlock;
import net.minecraft.block.OxidizableBulbBlock;
import net.minecraft.block.OxidizableChainBlock;
import net.minecraft.block.OxidizableCopperChestBlock;
import net.minecraft.block.OxidizableCopperGolemStatueBlock;
import net.minecraft.block.OxidizableDoorBlock;
import net.minecraft.block.OxidizableGrateBlock;
import net.minecraft.block.OxidizableLanternBlock;
import net.minecraft.block.OxidizableLightningRodBlock;
import net.minecraft.block.OxidizablePaneBlock;
import net.minecraft.block.OxidizableSlabBlock;
import net.minecraft.block.OxidizableStairsBlock;
import net.minecraft.block.OxidizableTrapdoorBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({
    OxidizableBlock.class,
    OxidizableBulbBlock.class,
    OxidizableChainBlock.class,
    OxidizableCopperChestBlock.class,
    OxidizableCopperGolemStatueBlock.class,
    OxidizableDoorBlock.class,
    OxidizableGrateBlock.class,
    OxidizableLanternBlock.class,
    OxidizableLightningRodBlock.class,
    OxidizablePaneBlock.class,
    OxidizableSlabBlock.class,
    OxidizableStairsBlock.class,
    OxidizableTrapdoorBlock.class
})
public abstract class OxidizableBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        // if rule is enabled, loop through adjacent blocks
        if(MiniTweaksSettings.fasterOxidation) {
            for(Direction dir : Direction.values()) {
                // instead of checking down, check the block itself, for waterlogged blocks
                BlockPos waterOffset = dir == Direction.DOWN ? pos : pos.offset(dir);

                // check if any touching block has water (except down)
                // down isn't included due to water not touching the blocks above it visually
                if(world.getFluidState(waterOffset).isIn(FluidTags.WATER) && state.getBlock() instanceof Oxidizable oxidizable) {
                    // get oxidation result and place block
                    oxidizable.getDegradationResult(state).ifPresent(oxidizeState -> world.setBlockState(pos, oxidizeState));
                    ci.cancel();
                }
            }
        }
    }
}
