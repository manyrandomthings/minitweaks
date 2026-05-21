package minitweaks.dispenser.behaviors;

import minitweaks.mixins.mob.shulker.dye.ShulkerEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import java.util.List;
import java.util.Optional;

public class WaterBottleDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);

        ServerLevel serverWorld = pointer.level();
        BlockPos blockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));

        // get all dyed shulkers in front of dispenser
        List<Shulker> list = serverWorld.getEntities(EntityType.SHULKER, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((livingEntity) -> {
            return ((Shulker) livingEntity).getColor() != null;
        }));

        // check if there are any shulkers
        if(!list.isEmpty()) {
            // get random shulker, set its color to undyed
            Shulker randomShulker = Util.getRandom(list, serverWorld.getRandom());
            ((ShulkerEntityInvoker) randomShulker).invokeSetColor(Optional.empty());

            // try to add new item to inventory, dispense if full
            return this.consumeWithRemainder(pointer, stack, new ItemStack(Items.GLASS_BOTTLE));
        }

        // no dyed shulkers in front of dispenser
        this.setSuccess(false);
        return stack;
    }
}
