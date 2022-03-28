package minitweaks.dispenser.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public class NameTagDispenserBehavior extends FallibleItemDispenserBehavior {
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        // get all non-player living entities in front of dispenser
        List<LivingEntity> list = pointer.getWorld().getEntitiesByClass(LivingEntity.class, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY.and((livingEntity) -> !(livingEntity instanceof PlayerEntity)));

        // if mobs found
        if(!list.isEmpty()) {
            // get random entity
            LivingEntity entity = Util.getRandom(list, pointer.getWorld().getRandom());
            // set name to nametag's name
            entity.setCustomName(stack.getName());
            // if entity is MobEntity, prevent it from despawning
            if(entity instanceof MobEntity mobEntity) {
                mobEntity.setPersistent();
            }
            stack.decrement(1);
            return stack;
        }

        // name tag is named but no mobs are in front, do nothing
        this.setSuccess(false);
        return stack;
    }
}
