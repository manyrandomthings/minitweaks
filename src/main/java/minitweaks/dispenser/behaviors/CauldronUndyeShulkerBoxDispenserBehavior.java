package minitweaks.dispenser.behaviors;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronUndyeShulkerBoxDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        World world = pointer.getWorld();
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState blockState = world.getBlockState(blockPos);
        CauldronBlock cauldronBlock = (CauldronBlock) blockState.getBlock();
        int level = blockState.get(CauldronBlock.LEVEL);

        // check if cauldron is not empty and item is not an undyed shulker box
        if(level > 0 && stack.getItem() != Items.SHULKER_BOX) {
            // create undyed shulker box, copy contents, and decrease cauldron level by 1
            ItemStack undyedShulkerBox = new ItemStack(Items.SHULKER_BOX);
            if(stack.hasTag()) {
                undyedShulkerBox.setTag(stack.getTag().copy());
            }
            cauldronBlock.setLevel(world, blockPos, blockState, level - 1);

            stack.decrement(1);

            // if dyed shulker stack is empty, return undyed shulker
            if(stack.isEmpty()) {
                return undyedShulkerBox;
            }

            // try to add undyed shulker to dispenser's inventory
            DispenserBlockEntity dispenserBlockEntity = pointer.getBlockEntity();
            int addedToSlot = dispenserBlockEntity.addToFirstFreeSlot(undyedShulkerBox);

            // check if undyed shulker was added to inventory, if not then dispense
            if(addedToSlot < 0) {
                super.dispenseSilently(pointer, undyedShulkerBox);
            }


            // return dyed shulker box stack
            return stack;
        }

        // fail to dispense item
        this.setSuccess(false);
        return stack;
    }
}
