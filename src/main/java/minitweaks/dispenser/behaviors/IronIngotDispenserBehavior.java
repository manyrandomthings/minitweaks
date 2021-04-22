package minitweaks.dispenser.behaviors;

import java.util.List;
import java.util.Random;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class IronIngotDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        // get all iron golems in front of dispenser
        List<IronGolemEntity> list = pointer.getWorld().getEntitiesByType(EntityType.IRON_GOLEM, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY);

        // if mobs found
        for(IronGolemEntity ironGolem : list) {
            if(ironGolem.getHealth() < ironGolem.getMaxHealth()) {
                // heal golem
                ironGolem.heal(25.0F);

                // play repair sound
                Random rand = ironGolem.getRandom();
                float pitch = 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F;
                ironGolem.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, pitch);

                // remove one ingot and return
                stack.decrement(1);
                return stack;
            }
        }

        this.setSuccess(false);
        return stack;
    }
}
