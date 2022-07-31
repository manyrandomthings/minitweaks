package minitweaks.mixins.block.shrieker.activate;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.block.all.AbstractBlockMixin;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlock.class)
public abstract class SculkShriekerBlockMixin extends AbstractBlockMixin {
    @Override
    protected void onUseInject(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        // if rule is enabled
        if(MiniTweaksSettings.echoShardsEnableShriekers) {
            ItemStack heldItem = player.getStackInHand(hand);

            // check if item is echo shard and being used on a sculk shrieker
            if(heldItem.isOf(Items.ECHO_SHARD) && state.isOf(Blocks.SCULK_SHRIEKER)) {
                // check if shrieker can already summon wardens
                if(!state.get(SculkShriekerBlock.CAN_SUMMON)) {
                    // remove echo shard if in survival
                    if(!player.getAbilities().creativeMode) {
                        heldItem.decrement(1);
                    }
                    // set can_summon state to true
                    world.setBlockState(pos, state.with(SculkShriekerBlock.CAN_SUMMON, true));

                    // swing arm
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
                else {
                    // if shrieker can already summon, ignore click
                    cir.setReturnValue(ActionResult.CONSUME);
                }
            }
        }
    }
}
