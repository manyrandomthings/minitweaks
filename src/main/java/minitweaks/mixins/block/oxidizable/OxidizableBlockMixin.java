package minitweaks.mixins.block.oxidizable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.OxidizableBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OxidizableBlock.class)
public abstract class OxidizableBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
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
