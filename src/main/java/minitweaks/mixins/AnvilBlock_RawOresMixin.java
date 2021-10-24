package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaksSettings;
import minitweaks.util.AnvilCrushing;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(AnvilBlock.class)
public class AnvilBlock_RawOresMixin {
    @Inject(method = "onLanding", at = @At("HEAD"))
    private void convertBlocks(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity, CallbackInfo ci) {
        // check if rule enabled
        if(MiniTweaksSettings.renewableRawOres) {
            AnvilCrushing.tryRawOreCrush(world, pos.down());
        }
    }

    @Inject(method = "onDestroyedOnLanding", at = @At("HEAD"))
    private void convertLandingDestroyed(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity, CallbackInfo ci) {
        // check if rule enabled and anvil can fall through block
        if(MiniTweaksSettings.renewableRawOres && FallingBlock.canFallThrough(world.getBlockState(pos))) {
            AnvilCrushing.tryRawOreCrush(world, pos.down());
        }
    }
}
