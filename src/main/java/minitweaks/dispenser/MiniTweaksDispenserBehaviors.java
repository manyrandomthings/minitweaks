package minitweaks.dispenser;

import minitweaks.MiniTweaksSettings;
import minitweaks.dispenser.behaviors.CauldronBannerLayerDispenserBehavior;
import minitweaks.dispenser.behaviors.CauldronEmptyBucketDispenserBehavior;
import minitweaks.dispenser.behaviors.CauldronGlassBottleDispenserBehavior;
import minitweaks.dispenser.behaviors.CauldronUndyeItemDispenserBehavior;
import minitweaks.dispenser.behaviors.CauldronUndyeShulkerBoxDispenserBehavior;
import minitweaks.dispenser.behaviors.CauldronWaterBottleDispenserBehavior;
import minitweaks.dispenser.behaviors.CauldronWaterBucketDispenserBehavior;
import minitweaks.dispenser.behaviors.DyeItemDispenserBehavior;
import minitweaks.dispenser.behaviors.GoldenAppleDispenserBehavior;
import minitweaks.dispenser.behaviors.IronIngotDispenserBehavior;
import minitweaks.dispenser.behaviors.NameTagDispenserBehavior;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
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
    public static final DispenserBehavior CAULDRON_WATER_BUCKET = new CauldronWaterBucketDispenserBehavior();
    public static final DispenserBehavior CAULDRON_EMPTY_BUCKET = new CauldronEmptyBucketDispenserBehavior();
    public static final DispenserBehavior CAULDRON_WATER_BOTTLE = new CauldronWaterBottleDispenserBehavior();
    public static final DispenserBehavior CAULDRON_GLASS_BOTTLE = new CauldronGlassBottleDispenserBehavior();
    public static final DispenserBehavior CAULDRON_UNDYE_ITEM = new CauldronUndyeItemDispenserBehavior();
    public static final DispenserBehavior CAULDRON_REMOVE_BANNER_LAYER = new CauldronBannerLayerDispenserBehavior();
    public static final DispenserBehavior CAULDRON_UNDYE_SHULKER_BOX = new CauldronUndyeShulkerBoxDispenserBehavior();

    // get dispenser behavior
    public static DispenserBehavior getCustomDispenserBehavior(ServerWorld serverWorld, BlockPos pos, BlockPointer blockPointer, DispenserBlockEntity dispenserBlockEntity, ItemStack stack) {
        Item item = stack.getItem();
        BlockPos frontPos = pos.offset(blockPointer.getBlockState().get(DispenserBlock.FACING));
        Box frontBox = new Box(frontPos);
        Block frontBlock = serverWorld.getBlockState(frontPos).getBlock();

        // name tag (with name) behavior
        if(MiniTweaksSettings.dispensersNameMobs && item == Items.NAME_TAG && stack.hasCustomName()) {
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
        else if(MiniTweaksSettings.dispensersCureVillagers && item == Items.GOLDEN_APPLE) {
            boolean hasZombieVillagers = !serverWorld.getEntitiesByType(EntityType.ZOMBIE_VILLAGER, frontBox, EntityPredicates.VALID_LIVING_ENTITY).isEmpty();

            if(hasZombieVillagers) {
                return GOLDEN_APPLE;
            }
        }
        // iron ingot behavior
        else if(MiniTweaksSettings.dispensersRepairGolems && item == Items.IRON_INGOT) {
            boolean hasIronGolems = !serverWorld.getEntitiesByType(EntityType.IRON_GOLEM, frontBox, EntityPredicates.VALID_LIVING_ENTITY).isEmpty();

            if(hasIronGolems) {
                return IRON_INGOT;
            }
        }
        // cauldron behaviors
        else if(MiniTweaksSettings.dispensersUseCauldrons && frontBlock == Blocks.CAULDRON) {
            if(item == Items.WATER_BUCKET) {
                return CAULDRON_WATER_BUCKET;
            }
            else if(item == Items.BUCKET) {
                return CAULDRON_EMPTY_BUCKET;
            }
            else if(item == Items.POTION && PotionUtil.getPotion(stack) == Potions.WATER) {
                return CAULDRON_WATER_BOTTLE;
            }
            else if(item == Items.GLASS_BOTTLE) {
                return CAULDRON_GLASS_BOTTLE;
            }
            else if(item instanceof DyeableItem) {
                return CAULDRON_UNDYE_ITEM;
            }
            else if(item instanceof BannerItem) {
                return CAULDRON_REMOVE_BANNER_LAYER;
            }
            else if(Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
                return CAULDRON_UNDYE_SHULKER_BOX;
            }
        }

        // no available dispenser behaviors, return null
        return null;
    }
}
