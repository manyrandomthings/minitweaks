package mini_tweaks;

import java.util.List;

import mini_tweaks.mixins.ZombieVillagerEntity_setConvertingAccessorMixin;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class DispenserBehaviorRules {
    public static void init() {
        // dispensers name mobs rule
        DispenserBlock.registerBehavior(Items.NAME_TAG.asItem(), new FallibleItemDispenserBehavior() {
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                // if rule disabled or item has no name, use default dispenser behavior
                if(!MiniTweaksSettings.dispensersNameMobs || !stack.hasCustomName()) {
                    this.setSuccess(true);
                    return super.dispenseSilently(pointer, stack);
                }

                // get block in front of dispenser
                BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                // get all non-player living entities in front of dispenser
                List<LivingEntity> list = pointer.getWorld().getEntitiesByClass(LivingEntity.class, new Box(blockPos), (livingEntity) -> {
                    return !(livingEntity instanceof PlayerEntity) && livingEntity.isAlive();
                });

                // if mobs found
                if(!list.isEmpty()) {
                    // get first entity found
                    LivingEntity entity = list.get(0);
                    // set name to nametag's name
                    entity.setCustomName(stack.getName());
                    // if entity is MobEntity, prevent it from despawning
                    if(entity instanceof MobEntity) {
                        ((MobEntity) entity).setPersistent();
                    }
                    stack.decrement(1);
                    this.setSuccess(true);
                    return stack;
                }

                // name tag is named but no mobs are in front, do nothing
                this.setSuccess(false);
                return stack;
            }
        });

        // dispensers dye sheep rule
        for(DyeColor dyeColor : DyeColor.values()) {
            DispenserBlock.registerBehavior(DyeItem.byColor(dyeColor).asItem(), new FallibleItemDispenserBehavior() {
                public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                    if(!MiniTweaksSettings.dispensersDyeSheep) {
                        this.setSuccess(true);
                        return super.dispenseSilently(pointer, stack);
                    }

                    // get block in front of dispenser
                    BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                    // get all sheep entities in front of dispenser
                    List<SheepEntity> list = pointer.getWorld().getEntitiesByClass(SheepEntity.class, new Box(blockPos), (sheepEntity) -> {
                        // select only alive sheep
                        return sheepEntity.isAlive();
                    });

                    // go through list of sheep
                    for(SheepEntity sheepEntity : list) {
                        // get color of item
                        DyeColor itemColor = ((DyeItem) stack.getItem()).getColor();

                        // if sheep is not sheared and not the same color as item
                        if(!sheepEntity.isSheared() && sheepEntity.getColor() != itemColor) {
                            // set sheep color to item color
                            sheepEntity.setColor(itemColor);
                            stack.decrement(1);
                            this.setSuccess(true);
                            return stack;
                        }
                    }

                    // fail to dispense if no sheep are available to be dyed
                    this.setSuccess(false);
                    return stack;
                }
            });
        }

        // dispensers cure villagers rule
        DispenserBlock.registerBehavior(Items.GOLDEN_APPLE, new FallibleItemDispenserBehavior() {
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                // if rule disabled, use default dispenser behavior
                if(!MiniTweaksSettings.dispensersCureVillagers) {
                    this.setSuccess(true);
                    return super.dispenseSilently(pointer, stack);
                }

                // get block in front of dispenser
                BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                // get all zombie villagers in front of dispenser
                List<ZombieVillagerEntity> list = pointer.getWorld().getEntitiesByClass(ZombieVillagerEntity.class, new Box(blockPos), (zombieVillager) -> {
                    return zombieVillager.isAlive();
                });

                // if mobs found
                for(ZombieVillagerEntity zombieVillager : list) {
                    if(!zombieVillager.isConverting() && zombieVillager.hasStatusEffect(StatusEffects.WEAKNESS)) {
                        ((ZombieVillagerEntity_setConvertingAccessorMixin) zombieVillager).invokeSetConverting(null, zombieVillager.getRandom().nextInt(2401) + 3600);
                        stack.decrement(1);
                        this.setSuccess(true);
                        return stack;
                    }
                }
                this.setSuccess(false);
                return stack;
            }
        });
    }
}
