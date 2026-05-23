package minitweaks.dispenser.behaviors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class WaterBucketDispenserBehavior extends OptionalDispenseItemBehavior {
    protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        // get all bucketable mobs in front of dispenser
        List<LivingEntity> list = blockSource.level().getEntitiesOfClass(LivingEntity.class, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((livingEntity) -> {
            return livingEntity instanceof Bucketable;
        }));

        if(!list.isEmpty()) {
            // get random bucketable mob in list
            LivingEntity livingEntity = Util.getRandom(list, blockSource.level().getRandom());
            Bucketable bucketable = (Bucketable) livingEntity;

            // play bucket sound, get bucket item
            livingEntity.playSound(bucketable.getPickupSound(), 1.0F, 1.0F);
            ItemStack mobBucketItem = bucketable.getBucketItemStack();
            bucketable.saveToBucketTag(mobBucketItem);

            // remove bucketed mob
            livingEntity.discard();

            // return bucket item
            return mobBucketItem;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
