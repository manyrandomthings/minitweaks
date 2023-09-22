package minitweaks.dispenser.behaviors;

import minitweaks.mixins.mob.allay.duplicate.AllayEntityInvoker;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public class AmethystShardDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);

        // get block in front of dispenser
        BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        // get valid allays in front of dispenser
        List<AllayEntity> list = pointer.world().getEntitiesByType(EntityType.ALLAY, new Box(blockPos), EntityPredicates.VALID_LIVING_ENTITY.and((entity) -> {
            AllayEntity allayEntity = (AllayEntity) entity;
            AllayEntityInvoker allayEntityInvoker = (AllayEntityInvoker) allayEntity;
            return allayEntity.isDancing() && allayEntityInvoker.invokeMatchesDuplicationIngredient(stack) && allayEntityInvoker.invokeCanDuplicate();
        }));

        if(!list.isEmpty()) {
            ServerWorld serverWorld = pointer.world();
            AllayEntity randomAllay = Util.getRandom(list, serverWorld.getRandom());
            AllayEntityInvoker allayInvoker = (AllayEntityInvoker) randomAllay;

            allayInvoker.invokeDuplicate();
            serverWorld.sendEntityStatus(randomAllay, (byte) 18);
            serverWorld.playSoundFromEntity(null, randomAllay, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.NEUTRAL, 2.0F, 1.0F);

            stack.decrement(1);
            return stack;
        }

        this.setSuccess(false);
        return stack;
    }
}
