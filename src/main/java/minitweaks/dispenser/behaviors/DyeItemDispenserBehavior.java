package minitweaks.dispenser.behaviors;

import java.util.List;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.ShulkerEntity_GetColorInvokerMixin;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class DyeItemDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        // get all PathAwareEntity entities in front of dispenser (common root of sheep and shulkers)
        List<PathAwareEntity> list = pointer.getWorld().getEntitiesByClass(PathAwareEntity.class, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY);
        // get color of item
        DyeColor itemColor = ((DyeItem) stack.getItem()).getColor();

        // go through list of entities
        for(PathAwareEntity entity : list) {
            if(entity instanceof SheepEntity) {
                SheepEntity sheepEntity = (SheepEntity) entity;
                // if sheep is not sheared and not the same color as item
                if(!sheepEntity.isSheared() && sheepEntity.getColor() != itemColor) {
                    // set sheep color to item color
                    sheepEntity.setColor(itemColor);
                    stack.decrement(1);
                    return stack;
                }
            }
            else if(MiniTweaksSettings.dyeableShulkers && entity instanceof ShulkerEntity) {
                ShulkerEntity shulkerEntity = (ShulkerEntity) entity;

                if(shulkerEntity.getColor() != itemColor) {
                    ((ShulkerEntity_GetColorInvokerMixin) shulkerEntity).invokeSetColor(itemColor);
                    stack.decrement(1);
                    return stack;
                }
            }
        }

        // fail to dispense if no entities are available to be dyed
        this.setSuccess(false);
        return stack;
    }
}
