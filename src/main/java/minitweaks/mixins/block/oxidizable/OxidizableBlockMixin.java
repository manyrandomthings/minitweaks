package minitweaks.mixins.block.oxidizable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.OxidizableBlock;
import net.minecraft.block.OxidizableBulbBlock;
import net.minecraft.block.OxidizableDoorBlock;
import net.minecraft.block.OxidizableGrateBlock;
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

class OxidationHelper {
    static void applyOxidation(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        // if rule is enabled, loop through adjacent blocks
        if(MiniTweaksSettings.fasterOxidation) {
            for(Direction dir : Direction.values()) {
                // check if any touching block has water (except down)
                if(dir != Direction.DOWN && world.getFluidState(pos.offset(dir)).isIn(FluidTags.WATER) && state.getBlock() instanceof Oxidizable oxidizable) {
                    // get oxidation result and place block
                    oxidizable.getDegradationResult(state).ifPresent(oxidizeState -> world.setBlockState(pos, oxidizeState));
                    ci.cancel();
                }
            }
        }        
    }
}

@Mixin(OxidizableBlock.class)
public abstract class OxidizableBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        applyOxidation(state, world, pos, random, ci);
    }
}


@Mixin(OxidizableBulbBlock.class)
public abstract class OxidizableBulbBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        applyOxidation(state, world, pos, random, ci);
    }
}


@Mixin(OxidizableDoorBlock.class)
public abstract class OxidizableDoorBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        applyOxidation(state, world, pos, random, ci);
    }
}

@Mixin(OxidizableGrateBlock.class)
public abstract class OxidizableGrateBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        applyOxidation(state, world, pos, random, ci);
    }
}

@Mixin(OxidizableSlabBlock.class)
public abstract class OxidizableSlabBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        applyOxidation(state, world, pos, random, ci);
    }
}

@Mixin(OxidizableStairsBlock.class)
public abstract class OxidizableStairsBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        applyOxidation(state, world, pos, random, ci);
    }
}

@Mixin(OxidizableTrapdoorBlock.class)
public abstract class OxidizableTrapdoorBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        applyOxidation(state, world, pos, random, ci);
    }
}
