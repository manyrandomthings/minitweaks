package minitweaks.dispenser;

import minitweaks.MiniTweaksSettings;
import minitweaks.dispenser.behaviors.AmethystShardDispenserBehavior;
import minitweaks.dispenser.behaviors.DyeItemDispenserBehavior;
import minitweaks.dispenser.behaviors.GoldenAppleDispenserBehavior;
import minitweaks.dispenser.behaviors.IronIngotDispenserBehavior;
import minitweaks.dispenser.behaviors.NameTagDispenserBehavior;
import minitweaks.dispenser.behaviors.WaterBottleDispenserBehavior;
import minitweaks.dispenser.behaviors.WaterBucketDispenserBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.phys.AABB;

public class MiniTweaksDispenserBehaviors {
    public static final DispenseItemBehavior NAME_TAG = new NameTagDispenserBehavior();
    public static final DispenseItemBehavior DYE_ITEM = new DyeItemDispenserBehavior();
    public static final DispenseItemBehavior GOLDEN_APPLE = new GoldenAppleDispenserBehavior();
    public static final DispenseItemBehavior IRON_INGOT = new IronIngotDispenserBehavior();
    public static final DispenseItemBehavior WATER_BUCKET = new WaterBucketDispenserBehavior();
    public static final DispenseItemBehavior AMETHYST_SHARD = new AmethystShardDispenserBehavior();
    public static final DispenseItemBehavior WATER_BOTTLE = new WaterBottleDispenserBehavior();

    // get dispenser behavior
    public static DispenseItemBehavior getCustomDispenserBehavior(ServerLevel serverLevel, BlockPos pos, BlockSource blockSource, DispenserBlockEntity dispenserBlockEntity, ItemStack stack) {
        Item item = stack.getItem();
        BlockPos frontPos = pos.relative(blockSource.state().getValue(DispenserBlock.FACING));
        AABB frontArea = new AABB(frontPos);

        // name tag (with name) behavior
        if(MiniTweaksSettings.dispensersNameMobs && stack.is(Items.NAME_TAG) && stack.has(DataComponents.CUSTOM_NAME)) {
            boolean hasNameableMobs = !serverLevel.getEntitiesOfClass(LivingEntity.class, frontArea, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and(entity -> !(entity instanceof Player))).isEmpty();

            if(hasNameableMobs) {
                return NAME_TAG;
            }
        }
        // dye items behavior
        else if(MiniTweaksSettings.dispensersDyeMobs && item instanceof DyeItem) {
            boolean hasDyeableMobs = !serverLevel.getEntitiesOfClass(PathfinderMob.class, frontArea, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and(entity -> {
                return entity instanceof Sheep || (MiniTweaksSettings.dyeableShulkers && entity instanceof Shulker);
            })).isEmpty();

            if(hasDyeableMobs) {
                return DYE_ITEM;
            }
        }
        // undye shulker behavior
        else if(MiniTweaksSettings.dyeableShulkers && MiniTweaksSettings.dispensersDyeMobs && stack.is(Items.POTION) && stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.WATER)) {
            boolean hasShulkers = !serverLevel.getEntities(EntityType.SHULKER, frontArea, EntitySelector.LIVING_ENTITY_STILL_ALIVE).isEmpty();

            if(hasShulkers) {
                return WATER_BOTTLE;
            }
        }
        // golden apple behavior
        else if(MiniTweaksSettings.dispensersCureVillagers && stack.is(Items.GOLDEN_APPLE)) {
            boolean hasZombieVillagers = !serverLevel.getEntities(EntityType.ZOMBIE_VILLAGER, frontArea, EntitySelector.LIVING_ENTITY_STILL_ALIVE).isEmpty();

            if(hasZombieVillagers) {
                return GOLDEN_APPLE;
            }
        }
        // iron ingot behavior
        else if(MiniTweaksSettings.dispensersRepairGolems && stack.is(Items.IRON_INGOT)) {
            boolean hasIronGolems = !serverLevel.getEntities(EntityType.IRON_GOLEM, frontArea, EntitySelector.LIVING_ENTITY_STILL_ALIVE).isEmpty();

            if(hasIronGolems) {
                return IRON_INGOT;
            }
        }
        // pick up bucketable mob
        else if(MiniTweaksSettings.dispensersBucketMobs && stack.is(Items.WATER_BUCKET)) {
            boolean hasBucketableMobs = !serverLevel.getEntitiesOfClass(LivingEntity.class, frontArea, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and(entity -> {
                return entity instanceof Bucketable;
            })).isEmpty();

            if(hasBucketableMobs) {
                return WATER_BUCKET;
            }
        }
        else if(MiniTweaksSettings.dispensersDuplicateAllays && stack.is(Items.AMETHYST_SHARD)) {
            boolean hasAllays = !serverLevel.getEntities(EntityType.ALLAY, frontArea, EntitySelector.LIVING_ENTITY_STILL_ALIVE).isEmpty();

            if(hasAllays) {
                return AMETHYST_SHARD;
            }
        }

        // no available dispenser behaviors, return null
        return null;
    }
}
