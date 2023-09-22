package minitweaks.dispenser.behaviors;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Optional;

public class DyeItemDispenserBehavior extends FallibleItemDispenserBehavior {
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        // get color of item
        DyeColor itemColor = ((DyeItem) stack.getItem()).getColor();

        // get block in front of dispenser
        BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        // get list of valid entities in front of dispenser
        List<PathAwareEntity> list = pointer.world().getEntitiesByClass(PathAwareEntity.class, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY.and((entity) -> {
            // sheep must not be sheared (if dyeableShearedSheep is not enabled) or match item color
            if(entity instanceof SheepEntity sheepEntity) {
                return (MiniTweaksSettings.dyeableShearedSheep || !sheepEntity.isSheared()) && sheepEntity.getColor() != itemColor;
            }
            // dyeableShulkers rule must be enabled and shulker must not match item color
            else if(MiniTweaksSettings.dyeableShulkers && entity instanceof ShulkerEntity shulkerEntity) {
                return shulkerEntity.getColor() != itemColor;
            }
            return false;
        }));

        // check if there are valid entities
        if(!list.isEmpty()) {
            // choose random mob
            PathAwareEntity randomMob = Util.getRandom(list, pointer.world().getRandom());
            // play dye sound
            randomMob.getWorld().playSoundFromEntity(null, randomMob, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);

            // set color of sheep or shulker
            if(randomMob instanceof SheepEntity sheepEntity) {
                sheepEntity.setColor(itemColor);
            }
            else if(randomMob instanceof ShulkerEntity shulkerEntity) {
                shulkerEntity.setVariant(Optional.of(itemColor));
            }

            stack.decrement(1);
            return stack;
        }

        // fail to dispense if no entities are available to be dyed
        this.setSuccess(false);
        return stack;
    }
}
