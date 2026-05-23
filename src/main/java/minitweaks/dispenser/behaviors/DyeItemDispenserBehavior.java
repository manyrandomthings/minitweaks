package minitweaks.dispenser.behaviors;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.mob.shulker.dye.ShulkerInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import java.util.List;
import java.util.Optional;

public class DyeItemDispenserBehavior extends OptionalDispenseItemBehavior {
    protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
        this.setSuccess(true);
        // get color of item
        DyeColor dyeColor = ((DyeItem) stack.getItem()).getDyeColor();

        // get block in front of dispenser
        BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        // get list of valid entities in front of dispenser
        List<PathfinderMob> list = blockSource.level().getEntitiesOfClass(PathfinderMob.class, new AABB(blockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((entity) -> {
            // sheep must not be sheared (if dyeableShearedSheep is not enabled) or match item color
            if(entity instanceof Sheep sheep) {
                return (MiniTweaksSettings.dyeableShearedSheep || !sheep.isSheared()) && sheep.getColor() != dyeColor;
            }
            // dyeableShulkers rule must be enabled and shulker must not match item color
            else if(MiniTweaksSettings.dyeableShulkers && entity instanceof Shulker shulker) {
                return shulker.getColor() != dyeColor;
            }
            return false;
        }));

        // check if there are valid entities
        if(!list.isEmpty()) {
            // choose random mob
            PathfinderMob randomMob = Util.getRandom(list, blockSource.level().getRandom());
            // play dye sound
            randomMob.level().playSound(null, randomMob, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

            // set color of sheep or shulker
            if(randomMob instanceof Sheep sheep) {
                sheep.setColor(dyeColor);
            }
            else if(randomMob instanceof Shulker shulker) {
                ((ShulkerInvoker) shulker).invokeSetVariant(Optional.of(dyeColor));
            }

            stack.shrink(1);
            return stack;
        }

        // fail to dispense if no entities are available to be dyed
        this.setSuccess(false);
        return stack;
    }
}
