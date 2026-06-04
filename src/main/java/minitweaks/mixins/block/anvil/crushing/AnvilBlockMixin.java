package minitweaks.mixins.block.anvil.crushing;

import minitweaks.MiniTweaksSettings;
import minitweaks.util.AnvilCrushing;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @Inject(method = "onLand", at = @At("HEAD"))
    private void convertBlocks(Level level, BlockPos pos, BlockState state, BlockState replacedBlock, FallingBlockEntity entity, CallbackInfo ci) {
        // check if rule enabled
        if(MiniTweaksSettings.renewableRawOres) {
            AnvilCrushing.tryRawOreCrush(level, pos.below());
        }
    }

    @Inject(method = "onBrokenAfterFall", at = @At("HEAD"))
    private void convertLandingDestroyed(Level level, BlockPos pos, FallingBlockEntity entity, CallbackInfo ci) {
        // check if rule enabled and anvil can fall through block
        if(MiniTweaksSettings.renewableRawOres && FallingBlock.isFree(level.getBlockState(pos))) {
            AnvilCrushing.tryRawOreCrush(level, pos.below());
        }
    }
}
