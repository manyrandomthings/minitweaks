package minitweaks.dispenser.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Optional;

public class WaterBottleDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        ServerWorld serverWorld = pointer.getWorld();
        BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));

        // get all dyed shulkers in front of dispenser
        List<ShulkerEntity> list = serverWorld.getEntitiesByType(EntityType.SHULKER, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY.and((livingEntity) -> {
            return ((ShulkerEntity) livingEntity).getColor() != null;
        }));

        // check if there are any shulkers
        if(!list.isEmpty()) {
            // get random shulker, set its color to undyed
            ShulkerEntity randomShulker = Util.getRandom(list, serverWorld.getRandom());
            randomShulker.setVariant(Optional.empty());

            // decrement water bottle, create glass bottle
            stack.decrement(1);
            ItemStack newStack = new ItemStack(Items.GLASS_BOTTLE);

            // return glass bottle if stack is empty
            if(stack.isEmpty()) {
                return newStack;
            }

            // try to add new item to inventory, dispense if full
            if(((DispenserBlockEntity) pointer.getBlockEntity()).addToFirstFreeSlot(newStack) < 0) {
                super.dispenseSilently(pointer, newStack);
            }

            return stack;
        }

        // no dyed shulkers in front of dispenser
        this.setSuccess(false);
        return stack;
    }
}
