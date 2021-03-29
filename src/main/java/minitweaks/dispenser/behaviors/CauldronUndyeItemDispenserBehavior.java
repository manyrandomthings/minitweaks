package minitweaks.dispenser.behaviors;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronUndyeItemDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        World world = pointer.getWorld();
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState blockState = world.getBlockState(blockPos);
        CauldronBlock cauldronBlock = (CauldronBlock) blockState.getBlock();
        int level = blockState.get(CauldronBlock.LEVEL);
        DyeableItem dyeableItem = (DyeableItem) stack.getItem();

        // check if cauldron is not empty and item is dyed
        if(level > 0 && dyeableItem.hasColor(stack)) {
            // remove color and decrease cauldron level by 1
            dyeableItem.removeColor(stack);
            cauldronBlock.setLevel(world, blockPos, blockState, level - 1);

            // return undyed item
            return stack;
        }

        // fail to dispense item
        this.setSuccess(false);
        return stack;
    }
}
