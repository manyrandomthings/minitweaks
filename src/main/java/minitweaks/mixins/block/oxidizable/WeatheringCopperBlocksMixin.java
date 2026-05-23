package minitweaks.mixins.block.oxidizable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperBarsBlock;
import net.minecraft.world.level.block.WeatheringCopperBulbBlock;
import net.minecraft.world.level.block.WeatheringCopperChainBlock;
import net.minecraft.world.level.block.WeatheringCopperChestBlock;
import net.minecraft.world.level.block.WeatheringCopperDoorBlock;
import net.minecraft.world.level.block.WeatheringCopperFullBlock;
import net.minecraft.world.level.block.WeatheringCopperGolemStatueBlock;
import net.minecraft.world.level.block.WeatheringCopperGrateBlock;
import net.minecraft.world.level.block.WeatheringCopperSlabBlock;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import net.minecraft.world.level.block.WeatheringCopperTrapDoorBlock;
import net.minecraft.world.level.block.WeatheringLanternBlock;
import net.minecraft.world.level.block.WeatheringLightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({
    WeatheringCopperFullBlock.class,
    WeatheringCopperBulbBlock.class,
    WeatheringCopperChainBlock.class,
    WeatheringCopperChestBlock.class,
    WeatheringCopperGolemStatueBlock.class,
    WeatheringCopperDoorBlock.class,
    WeatheringCopperGrateBlock.class,
    WeatheringLanternBlock.class,
    WeatheringLightningRodBlock.class,
    WeatheringCopperBarsBlock.class,
    WeatheringCopperSlabBlock.class,
    WeatheringCopperStairBlock.class,
    WeatheringCopperTrapDoorBlock.class
})
public abstract class WeatheringCopperBlocksMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        // if rule is enabled, loop through adjacent blocks
        if(MiniTweaksSettings.fasterOxidation) {
            for(Direction dir : Direction.values()) {
                // instead of checking down, check the block itself, for waterlogged blocks
                BlockPos waterOffset = dir == Direction.DOWN ? pos : pos.relative(dir);

                // check if any touching block has water (except down)
                // down isn't included due to water not touching the blocks above it visually
                if(level.getFluidState(waterOffset).is(FluidTags.WATER) && state.getBlock() instanceof WeatheringCopper weatheringCopper) {
                    // get oxidation result and place block
                    weatheringCopper.getNext(state).ifPresent(oxidizeState -> level.setBlockAndUpdate(pos, oxidizeState));
                    ci.cancel();
                }
            }
        }
    }
}
