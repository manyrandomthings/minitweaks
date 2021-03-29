package minitweaks.dispenser.behaviors;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronGlassBottleDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        World world = pointer.getWorld();
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState blockState = world.getBlockState(blockPos);
        CauldronBlock cauldronBlock = (CauldronBlock) blockState.getBlock();
        int level = blockState.get(CauldronBlock.LEVEL);

        // check if cauldron is not empty
        if(level > 0) {
            // decrease cauldron level by 1 and play sound
            cauldronBlock.setLevel(world, blockPos, blockState, level - 1);
            world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);


            // create water bottle item stack
            ItemStack waterBottle = PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER);;
            stack.decrement(1);

            // if bottle stack is empty, return water bottle
            if(stack.isEmpty()) {
                return waterBottle;
            }

            // try to add water bottle to dispenser's inventory
            DispenserBlockEntity dispenserBlockEntity = pointer.getBlockEntity();
            int addedToSlot = dispenserBlockEntity.addToFirstFreeSlot(waterBottle);

            // check if water bottle was added to inventory, if not then dispense
            if(addedToSlot < 0) {
                super.dispenseSilently(pointer, waterBottle);
            }


            // return empty bottle stack
            return stack;
        }

        // fail to dispense water bottle
        this.setSuccess(false);
        return stack;
    }
}
