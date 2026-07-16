package minitweaks.dispenser.behaviors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class IronIngotDispenserBehavior extends OptionalDispenseItemBehavior {
    protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        // get all iron golems in front of dispenser
        List<IronGolem> list = blockSource.level().getEntities(EntityTypes.IRON_GOLEM, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((entity) -> {
            IronGolem ironGolem = (IronGolem) entity;
            return ironGolem.getHealth() < ironGolem.getMaxHealth();
        }));

        // if valid iron golems found
        if(!list.isEmpty()) {
            // get random golem
            IronGolem ironGolem = Util.getRandom(list, blockSource.level().getRandom());
            // heal golem
            ironGolem.heal(25.0F);

            // play repair sound
            RandomSource rand = ironGolem.getRandom();
            float pitch = 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F;
            ironGolem.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, pitch);

            // remove one ingot and return
            stack.shrink(1);
            return stack;
        }

        this.setSuccess(false);
        return stack;
    }
}
