package minitweaks.mixins;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ShovelItem.class)
public abstract class ShovelItem_PathMixin {
    // remove this class when 1.17 comes out

    private static final Map<Block, BlockState> EXTRA_PATH_STATES = Maps.newHashMap(ImmutableMap.of(
        Blocks.DIRT, Blocks.GRASS_PATH.getDefaultState(),
        Blocks.PODZOL, Blocks.GRASS_PATH.getDefaultState(),
        Blocks.COARSE_DIRT, Blocks.GRASS_PATH.getDefaultState(),
        Blocks.MYCELIUM, Blocks.GRASS_PATH.getDefaultState()
    ));

    @Shadow
    @Final
    private static Map<Block, BlockState> PATH_STATES;

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void dirtToPaths(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        Block block = blockState.getBlock();
        if(MiniTweaksSettings.morePaveableBlocks && !world.isClient && !PATH_STATES.containsKey(block)) {
            BlockState pathBlockState = EXTRA_PATH_STATES.get(block);
            if(pathBlockState != null && world.getBlockState(blockPos.up()).isAir()) {
                world.setBlockState(blockPos, pathBlockState, 11);
                world.playSound(null, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if(playerEntity != null) {
                    context.getStack().damage(1, playerEntity, (p) -> {
                        p.sendToolBreakStatus(context.getHand());
                    });
                }

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
