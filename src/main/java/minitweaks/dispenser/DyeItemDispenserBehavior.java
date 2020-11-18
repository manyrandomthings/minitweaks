package minitweaks.dispenser;

import java.util.List;

import minitweaks.MiniTweaksSettings;
import minitweaks.ShulkerEntityColorHelper;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
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
        List<PathAwareEntity> list = pointer.getWorld().getEntitiesByClass(PathAwareEntity.class, new Box(blockPos), (entity) -> {
            // select only alive mobs
            return entity.isAlive();
        });
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

                // replace with shulkerEntity.getColor() in 1.17
                if(ShulkerEntityColorHelper.getColor(shulkerEntity) != itemColor) {
                    // replace with shulkerEntity.setColor(itemColor) in 1.17
                    ShulkerEntityColorHelper.setColor(shulkerEntity, itemColor);
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
