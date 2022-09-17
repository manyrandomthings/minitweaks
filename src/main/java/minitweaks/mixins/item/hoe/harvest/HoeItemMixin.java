package minitweaks.mixins.item.hoe.harvest;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void harvestCrop(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        // check if rule is enabled and action is server side
        if(MiniTweaksSettings.quickHarvesting && !world.isClient) {
            // check if crop is mature
            if(isMature(state)) {
                // get usage tool (for fortune to apply)
                ItemStack tool = player != null ? player.getStackInHand(context.getHand()) : ItemStack.EMPTY;
                // get loot drops for crop
                List<ItemStack> droppedItems = Block.getDroppedStacks(state, (ServerWorld) world, pos, null, player, tool);
                boolean removedSeed = false;
                for(ItemStack itemStack : droppedItems) {
                    // if a seed hasn't been removed and item being dropped is the same as the crop being harvested, remove seed
                    if(!removedSeed && state.isOf(Block.getBlockFromItem(itemStack.getItem()))) {
                        // remove seed and set removed to true
                        itemStack.decrement(1);
                        removedSeed = true;
                    }
                    // drop item
                    Block.dropStack(world, pos, itemStack);
                }

                // if seed was removed from drops, update seed age to 0, otherwise place air
                BlockState postHarvestState = removedSeed ? state.with(getAgeProperty(state), 0) : Blocks.AIR.getDefaultState();
                world.setBlockState(pos, postHarvestState);

                // return success (swing arm)
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

    // check if crop is mature. Also returns false for invalid blocks being clicked
    private static boolean isMature(BlockState state) {
        Block block = state.getBlock();
        if(block instanceof CropBlock cropBlock) {
            return cropBlock.isMature(state);
        }
        else if(block instanceof NetherWartBlock) {
            return state.get(NetherWartBlock.AGE) == 3;
        }
        else if(block instanceof CocoaBlock) {
            return state.get(CocoaBlock.AGE) == 2;
        }
        return false;
    }

    // get age crop age property
    private static IntProperty getAgeProperty(BlockState blockState) {
        Block block = blockState.getBlock();

        if(block instanceof CropBlock cropBlock) {
            return cropBlock.getAgeProperty();
        }
        else if(block instanceof NetherWartBlock) {
            return NetherWartBlock.AGE;
        }
        else if(block instanceof CocoaBlock) {
            return CocoaBlock.AGE;
        }
        return null;
    }
}
