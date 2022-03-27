package minitweaks.dispenser.behaviors;

import java.util.List;

import minitweaks.mixins.mob.zombie.convert.ZombieVillagerEntityInvoker;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class GoldenAppleDispenserBehavior extends FallibleItemDispenserBehavior {
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        // get valid zombie villagers in front of dispenser
        List<ZombieVillagerEntity> list = pointer.getWorld().getEntitiesByClass(ZombieVillagerEntity.class, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY.and((entity) -> {
            ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) entity;
            return !zombieVillagerEntity.isConverting() && zombieVillagerEntity.hasStatusEffect(StatusEffects.WEAKNESS);
        }));

        if(!list.isEmpty()) {
            // choose random zombie villager
            ZombieVillagerEntity zombieVillager = Util.getRandom(list, pointer.getWorld().getRandom());
            // set converting
            ((ZombieVillagerEntityInvoker) zombieVillager).invokeSetConverting(null, zombieVillager.getRandom().nextInt(2401) + 3600);

            stack.decrement(1);
            return stack;
        }

        this.setSuccess(false);
        return stack;
    }
}
