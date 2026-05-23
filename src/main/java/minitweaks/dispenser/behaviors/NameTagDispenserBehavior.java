package minitweaks.dispenser.behaviors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class NameTagDispenserBehavior extends OptionalDispenseItemBehavior {
    protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        // get all non-player living entities in front of dispenser
        List<LivingEntity> list = blockSource.level().getEntitiesOfClass(LivingEntity.class, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((livingEntity) -> !(livingEntity instanceof Player)));

        // if mobs found
        if(!list.isEmpty()) {
            // get random entity
            LivingEntity entity = Util.getRandom(list, blockSource.level().getRandom());
            // set name to nametag's name
            entity.setCustomName(stack.getHoverName());
            // if entity is MobEntity, prevent it from despawning
            if(entity instanceof Mob mobEntity) {
                mobEntity.setPersistenceRequired();
            }
            stack.shrink(1);
            return stack;
        }

        // name tag is named but no mobs are in front, do nothing
        this.setSuccess(false);
        return stack;
    }
}
