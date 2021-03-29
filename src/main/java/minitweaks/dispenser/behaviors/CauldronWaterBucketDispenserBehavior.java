package minitweaks.dispenser.behaviors;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronWaterBucketDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        World world = pointer.getWorld();
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState blockState = world.getBlockState(blockPos);
        CauldronBlock cauldronBlock = (CauldronBlock) blockState.getBlock();
        int level = blockState.get(CauldronBlock.LEVEL);

        // check if cauldron is not full
        if(level < 3) {
            // fill cauldron and play sound
            cauldronBlock.setLevel(world, blockPos, blockState, 3);
            world.playSound(null, blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);

            // return empty bucket stack
            return new ItemStack(Items.BUCKET);
        }

        // fail to dispense water bucket
        this.setSuccess(false);
        return stack;
    }
}
