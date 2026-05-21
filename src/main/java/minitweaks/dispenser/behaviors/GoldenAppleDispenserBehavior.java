package minitweaks.dispenser.behaviors;

import minitweaks.mixins.mob.zombie.convert.ZombieVillagerEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class GoldenAppleDispenserBehavior extends OptionalDispenseItemBehavior {
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        // get valid zombie villagers in front of dispenser
        List<ZombieVillager> list = pointer.level().getEntitiesOfClass(ZombieVillager.class, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((entity) -> {
            ZombieVillager zombieVillagerEntity = (ZombieVillager) entity;
            return !zombieVillagerEntity.isConverting() && zombieVillagerEntity.hasEffect(MobEffects.WEAKNESS);
        }));

        if(!list.isEmpty()) {
            // choose random zombie villager
            ZombieVillager zombieVillager = Util.getRandom(list, pointer.level().getRandom());
            // set converting
            ((ZombieVillagerEntityInvoker) zombieVillager).invokeSetConverting(null, zombieVillager.getRandom().nextInt(2401) + 3600);

            stack.shrink(1);
            return stack;
        }

        this.setSuccess(false);
        return stack;
    }
}
