package minitweaks.dispenser.behaviors;

import minitweaks.mixins.mob.allay.duplicate.AllayEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class AmethystShardDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        // get valid allays in front of dispenser
        List<Allay> list = pointer.level().getEntities(EntityType.ALLAY, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((entity) -> {
            Allay allayEntity = (Allay) entity;
            AllayEntityInvoker allayEntityInvoker = (AllayEntityInvoker) allayEntity;
            return allayEntity.isDancing() && stack.is(ItemTags.DUPLICATES_ALLAYS) && allayEntityInvoker.invokeCanDuplicate();
        }));

        if(!list.isEmpty()) {
            ServerLevel serverWorld = pointer.level();
            Allay randomAllay = Util.getRandom(list, serverWorld.getRandom());
            AllayEntityInvoker allayInvoker = (AllayEntityInvoker) randomAllay;

            allayInvoker.invokeDuplicate();
            serverWorld.broadcastEntityEvent(randomAllay, (byte) 18);
            serverWorld.playSound(null, randomAllay, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 2.0F, 1.0F);

            stack.shrink(1);
            return stack;
        }

        this.setSuccess(false);
        return stack;
    }
}
