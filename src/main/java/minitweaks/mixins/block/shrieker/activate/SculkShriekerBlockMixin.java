package minitweaks.mixins.block.shrieker.activate;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.block.all.AbstractBlockMixin;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
    protected void onUseWithItemInject(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        // check if rule is enabled, item is echo shard, and is being used on a sculk shrieker with state can_summon=false
        if(MiniTweaksSettings.echoShardsEnableShriekers && stack.isOf(Items.ECHO_SHARD) && state.isOf(Blocks.SCULK_SHRIEKER) && !state.get(SculkShriekerBlock.CAN_SUMMON)) {
            // remove echo shard if in survival
            if(!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            // set can_summon state to true and play warden roar
            world.setBlockState(pos, state.with(SculkShriekerBlock.CAN_SUMMON, true));
            world.playSound(player, pos, SoundEvents.ENTITY_WARDEN_ROAR, SoundCategory.BLOCKS);

            // swing arm
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
