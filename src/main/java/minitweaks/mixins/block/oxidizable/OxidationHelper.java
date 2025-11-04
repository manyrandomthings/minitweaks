package minitweaks.mixins.block.oxidizable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
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

