package minitweaks.dispenser;

import minitweaks.MiniTweaksSettings;
import minitweaks.dispenser.behaviors.AmethystShardDispenserBehavior;
import minitweaks.dispenser.behaviors.DyeItemDispenserBehavior;
import minitweaks.dispenser.behaviors.GoldenAppleDispenserBehavior;
import minitweaks.dispenser.behaviors.IronIngotDispenserBehavior;
import minitweaks.dispenser.behaviors.NameTagDispenserBehavior;
import minitweaks.dispenser.behaviors.WaterBucketDispenserBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class MiniTweaksDispenserBehaviors {
    public static final DispenserBehavior NAME_TAG = new NameTagDispenserBehavior();
    public static final DispenserBehavior DYE_ITEM = new DyeItemDispenserBehavior();
    public static final DispenserBehavior GOLDEN_APPLE = new GoldenAppleDispenserBehavior();
    public static final DispenserBehavior IRON_INGOT = new IronIngotDispenserBehavior();
    public static final DispenserBehavior WATER_BUCKET = new WaterBucketDispenserBehavior();
    public static final DispenserBehavior AMETHYST_SHARD = new AmethystShardDispenserBehavior();

    // get dispenser behavior
    public static DispenserBehavior getCustomDispenserBehavior(ServerWorld serverWorld, BlockPos pos, BlockPointer blockPointer, DispenserBlockEntity dispenserBlockEntity, ItemStack stack) {
        Item item = stack.getItem();
        BlockPos frontPos = pos.offset(blockPointer.getBlockState().get(DispenserBlock.FACING));
        Box frontBox = new Box(frontPos);

        // name tag (with name) behavior
        if(MiniTweaksSettings.dispensersNameMobs && stack.isOf(Items.NAME_TAG) && stack.hasCustomName()) {
            boolean hasNameableMobs = !serverWorld.getEntitiesByClass(LivingEntity.class, frontBox, EntityPredicates.VALID_LIVING_ENTITY.and(entity -> !(entity instanceof PlayerEntity))).isEmpty();

            if(hasNameableMobs) {
                return NAME_TAG;
            }
        }
        // dye items behavior
        else if(MiniTweaksSettings.dispensersDyeMobs && item instanceof DyeItem) {
            boolean hasDyeableMobs = !serverWorld.getEntitiesByClass(PathAwareEntity.class, frontBox, EntityPredicates.VALID_LIVING_ENTITY.and(entity -> {
                return entity instanceof SheepEntity || (MiniTweaksSettings.dyeableShulkers && entity instanceof ShulkerEntity);
            })).isEmpty();

            if(hasDyeableMobs) {
                return DYE_ITEM;
            }
        }
        // golden apple behavior
        else if(MiniTweaksSettings.dispensersCureVillagers && stack.isOf(Items.GOLDEN_APPLE)) {
            boolean hasZombieVillagers = !serverWorld.getEntitiesByType(EntityType.ZOMBIE_VILLAGER, frontBox, EntityPredicates.VALID_LIVING_ENTITY).isEmpty();

            if(hasZombieVillagers) {
                return GOLDEN_APPLE;
            }
        }
        // iron ingot behavior
        else if(MiniTweaksSettings.dispensersRepairGolems && stack.isOf(Items.IRON_INGOT)) {
            boolean hasIronGolems = !serverWorld.getEntitiesByType(EntityType.IRON_GOLEM, frontBox, EntityPredicates.VALID_LIVING_ENTITY).isEmpty();

            if(hasIronGolems) {
                return IRON_INGOT;
            }
        }
        // pick up bucketable mob
        else if(MiniTweaksSettings.dispensersBucketMobs && stack.isOf(Items.WATER_BUCKET)) {
            boolean hasBucketableMobs = !serverWorld.getEntitiesByClass(LivingEntity.class, frontBox, EntityPredicates.VALID_LIVING_ENTITY.and(entity -> {
                return entity instanceof Bucketable;
            })).isEmpty();

            if(hasBucketableMobs) {
                return WATER_BUCKET;
            }
        }
        else if(MiniTweaksSettings.dispensersDuplicateAllays && stack.isOf(Items.AMETHYST_SHARD)) {
            boolean hasAllays = !serverWorld.getEntitiesByType(EntityType.ALLAY, frontBox, EntityPredicates.VALID_LIVING_ENTITY).isEmpty();

            if(hasAllays) {
                return AMETHYST_SHARD;
            }
        }

        // no available dispenser behaviors, return null
        return null;
    }
}
