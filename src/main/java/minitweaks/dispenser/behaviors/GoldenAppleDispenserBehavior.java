package minitweaks.dispenser.behaviors;

import minitweaks.mixins.mob.zombie.convert.ZombieVillagerInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class GoldenAppleDispenserBehavior extends OptionalDispenseItemBehavior {
    protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        // get valid zombie villagers in front of dispenser
        List<ZombieVillager> list = blockSource.level().getEntities(EntityType.ZOMBIE_VILLAGER, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((entity) -> {
            ZombieVillager zombieVillager = (ZombieVillager) entity;
            return !zombieVillager.isConverting() && zombieVillager.hasEffect(MobEffects.WEAKNESS);
        }));

        if(!list.isEmpty()) {
            // choose random zombie villager
            ZombieVillager zombieVillager = Util.getRandom(list, blockSource.level().getRandom());
            // set converting
            ((ZombieVillagerInvoker) zombieVillager).invokeStartConverting(null, zombieVillager.getRandom().nextInt(2401) + 3600);

            stack.shrink(1);
            return stack;
        }

        this.setSuccess(false);
        return stack;
    }
}
