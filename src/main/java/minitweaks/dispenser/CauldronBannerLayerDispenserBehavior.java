package minitweaks.dispenser;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronBannerLayerDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        World world = pointer.getWorld();
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState blockState = world.getBlockState(blockPos);
        CauldronBlock cauldronBlock = (CauldronBlock) blockState.getBlock();
        int level = blockState.get(CauldronBlock.LEVEL);

        // check if cauldron is not empty and banner has layers
        if(level > 0 && BannerBlockEntity.getPatternCount(stack) > 0) {
            // remove layer and decrease cauldron level by 1
            ItemStack updatedBannerStack = stack.copy();
            updatedBannerStack.setCount(1);
            BannerBlockEntity.loadFromItemStack(updatedBannerStack); //remove 1 banner layer. Yarn name is misleading.
            cauldronBlock.setLevel(world, blockPos, blockState, level - 1);

            stack.decrement(1);

            // if banner stack is empty, return new banner
            if(stack.isEmpty()) {
                return updatedBannerStack;
            }

            // try to add updated banner to dispenser's inventory
            DispenserBlockEntity dispenserBlockEntity = pointer.getBlockEntity();
            int addedToSlot = dispenserBlockEntity.addToFirstFreeSlot(updatedBannerStack);

            // check if updated banner was added to inventory, if not then dispense
            if(addedToSlot < 0) {
                super.dispenseSilently(pointer, updatedBannerStack);
            }


            // return banner stack
            return stack;
        }

        // fail to dispense item
        this.setSuccess(false);
        return stack;
    }
}
