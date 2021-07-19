package minitweaks.dispenser.behaviors;

import java.util.List;

import minitweaks.mixins.ZombieVillagerEntity_SetConvertingInvokerMixin;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class GoldenAppleDispenserBehavior extends FallibleItemDispenserBehavior {
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        // get all zombie villagers in front of dispenser
        List<ZombieVillagerEntity> list = pointer.getWorld().getEntitiesByClass(ZombieVillagerEntity.class, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY);

        // if mobs found
        for(ZombieVillagerEntity zombieVillager : list) {
            if(!zombieVillager.isConverting() && zombieVillager.hasStatusEffect(StatusEffects.WEAKNESS)) {
                ((ZombieVillagerEntity_SetConvertingInvokerMixin) zombieVillager).invokeSetConverting(null, zombieVillager.getRandom().nextInt(2401) + 3600);
                stack.decrement(1);
                return stack;
            }
        }

        this.setSuccess(false);
        return stack;
    }
}
