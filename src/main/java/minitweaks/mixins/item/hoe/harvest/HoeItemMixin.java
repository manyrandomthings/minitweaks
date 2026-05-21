package minitweaks.mixins.item.hoe.harvest;

import minitweaks.MiniTweaksSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void harvestCrop(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level world = context.getLevel();
        // check if rule is enabled and action is server side
        if(MiniTweaksSettings.quickHarvesting && !world.isClientSide()) {
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            Player player = context.getPlayer();

            // check if crop is mature
            if(isMature(state)) {
                // get usage tool (for fortune to apply)
                ItemStack tool = player != null ? player.getItemInHand(context.getHand()) : ItemStack.EMPTY;
                // get loot drops for crop
                List<ItemStack> droppedItems = Block.getDrops(state, (ServerLevel) world, pos, null, player, tool);
                boolean removedSeed = false;
                for(ItemStack itemStack : droppedItems) {
                    // if a seed hasn't been removed and item being dropped is the same as the crop being harvested, remove seed
                    if(!removedSeed && state.is(Block.byItem(itemStack.getItem()))) {
                        // remove seed and set removed to true
                        itemStack.shrink(1);
                        removedSeed = true;
                    }
                    // drop item
                    Block.popResource(world, pos, itemStack);
                }

                // create block breaking sound and particles
                world.destroyBlock(pos, false, player);

                // if seed was removed from drops, update seed age to 0, otherwise place air
                BlockState newCropState = getNewCrop(state);
                BlockState postHarvestState = removedSeed && newCropState != null ? newCropState : Blocks.AIR.defaultBlockState();
                world.setBlockAndUpdate(pos, postHarvestState);

                // return success (swing arm)
                cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
            }
        }
    }

    // check if crop is mature. Also returns false for invalid blocks being clicked
    private static boolean isMature(BlockState state) {
        Block block = state.getBlock();
        if(block instanceof CropBlock cropBlock) {
            return cropBlock.isMaxAge(state);
        }
        else if(block instanceof NetherWartBlock) {
            return state.getValue(NetherWartBlock.AGE) == 3;
        }
        else if(block instanceof CocoaBlock) {
            return state.getValue(CocoaBlock.AGE) == 2;
        }
        return false;
    }

    // get age 0 crop
    private static BlockState getNewCrop(BlockState blockState) {
        Block block = blockState.getBlock();

        if(block instanceof CropBlock cropBlock) {
            return cropBlock.getStateForAge(0);
        }
        else if(block instanceof NetherWartBlock) {
            return blockState.setValue(NetherWartBlock.AGE, 0);
        }
        else if(block instanceof CocoaBlock) {
            return blockState.setValue(CocoaBlock.AGE, 0);
        }
        return null;
    }
}
