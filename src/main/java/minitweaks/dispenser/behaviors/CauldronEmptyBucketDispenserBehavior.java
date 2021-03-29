package minitweaks.dispenser.behaviors;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronEmptyBucketDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        World world = pointer.getWorld();
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState blockState = world.getBlockState(blockPos);
        CauldronBlock cauldronBlock = (CauldronBlock) blockState.getBlock();
        int level = blockState.get(CauldronBlock.LEVEL);

        // check if cauldron is full
        if(level == 3) {
            // empty cauldron and play sound
            cauldronBlock.setLevel(world, blockPos, blockState, 0);
            world.playSound(null, blockPos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

            // create water bucket item stack
            ItemStack waterBucket = new ItemStack(Items.WATER_BUCKET);
            stack.decrement(1);

            // if bucket stack is empty, return water bucket
            if(stack.isEmpty()) {
                return waterBucket;
            }

            // try to add bucket to dispenser's inventory
            DispenserBlockEntity dispenserBlockEntity = pointer.getBlockEntity();
            int addedToSlot = dispenserBlockEntity.addToFirstFreeSlot(waterBucket);

            // check if water bucket was added to inventory, if not then dispense
            if(addedToSlot < 0) {
                super.dispenseSilently(pointer, waterBucket);
            }


            // return empty bucket stack
            return stack;
        }

        // fail to dispense water bucket
        this.setSuccess(false);
        return stack;
    }
}
