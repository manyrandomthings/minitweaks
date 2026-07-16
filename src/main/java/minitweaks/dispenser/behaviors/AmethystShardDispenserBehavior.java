package minitweaks.dispenser.behaviors;

import minitweaks.mixins.mob.allay.duplicate.AllayInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AmethystShardDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        // get valid allays in front of dispenser
        List<Allay> list = blockSource.level().getEntities(EntityTypes.ALLAY, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((entity) -> {
            Allay allay = (Allay) entity;
            return allay.isDancing() && stack.is(ItemTags.DUPLICATES_ALLAYS) && ((AllayInvoker) allay).invokeCanDuplicate();
        }));

        if(!list.isEmpty()) {
            ServerLevel serverLevel = blockSource.level();
            Allay randomAllay = Util.getRandom(list, serverLevel.getRandom());
            AllayInvoker allayInvoker = (AllayInvoker) randomAllay;

            allayInvoker.invokeDuplicateAllay();
            serverLevel.broadcastEntityEvent(randomAllay, (byte) 18);
            serverLevel.playSound(null, randomAllay, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 2.0F, 1.0F);

            stack.shrink(1);
            return stack;
        }

        this.setSuccess(false);
        return stack;
    }
}
