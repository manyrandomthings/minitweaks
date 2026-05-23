package minitweaks.mixins.block.shrieker.activate;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.block.all.BlockBehaviorMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlock.class)
public abstract class SculkShriekerBlockMixin extends BlockBehaviorMixin {
    @Override
    protected void onUseWithItemInject(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        // check if rule is enabled, item is echo shard, and is being used on a sculk shrieker with state can_summon=false
        if(MiniTweaksSettings.echoShardsEnableShriekers && stack.is(Items.ECHO_SHARD) && state.is(Blocks.SCULK_SHRIEKER) && !state.getValue(SculkShriekerBlock.CAN_SUMMON)) {
            // remove echo shard if in survival
            if(!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            // set can_summon state to true and play warden roar
            level.setBlockAndUpdate(pos, state.setValue(SculkShriekerBlock.CAN_SUMMON, true));
            level.playSound(player, pos, SoundEvents.WARDEN_ROAR, SoundSource.BLOCKS);

            // swing arm
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
