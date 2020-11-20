package minitweaks;

import minitweaks.dispenser.CauldronBannerLayerDispenserBehavior;
import minitweaks.dispenser.CauldronEmptyBucketDispenserBehavior;
import minitweaks.dispenser.CauldronGlassBottleDispenserBehavior;
import minitweaks.dispenser.CauldronUndyeItemDispenserBehavior;
import minitweaks.dispenser.CauldronUndyeShulkerBoxDispenserBehavior;
import minitweaks.dispenser.CauldronWaterBottleDispenserBehavior;
import minitweaks.dispenser.CauldronWaterBucketDispenserBehavior;
import minitweaks.dispenser.DyeItemDispenserBehavior;
import minitweaks.dispenser.GoldenAppleDispenserBehavior;
import minitweaks.dispenser.NameTagDispenserBehavior;
import net.minecraft.block.dispenser.DispenserBehavior;

public class MiniTweaksDispenserBehaviors {
    public static final DispenserBehavior NAME_TAG = new NameTagDispenserBehavior();
    public static final DispenserBehavior DYE_ITEM = new DyeItemDispenserBehavior();
    public static final DispenserBehavior GOLDEN_APPLE = new GoldenAppleDispenserBehavior();
    public static final DispenserBehavior CAULDRON_WATER_BUCKET = new CauldronWaterBucketDispenserBehavior();
    public static final DispenserBehavior CAULDRON_EMPTY_BUCKET = new CauldronEmptyBucketDispenserBehavior();
    public static final DispenserBehavior CAULDRON_WATER_BOTTLE = new CauldronWaterBottleDispenserBehavior();
    public static final DispenserBehavior CAULDRON_GLASS_BOTTLE = new CauldronGlassBottleDispenserBehavior();
    public static final DispenserBehavior CAULDRON_UNDYE_ITEM = new CauldronUndyeItemDispenserBehavior();
    public static final DispenserBehavior CAULDRON_REMOVE_BANNER_LAYER = new CauldronBannerLayerDispenserBehavior();
    public static final DispenserBehavior CAULDRON_UNDYE_SHULKER_BOX = new CauldronUndyeShulkerBoxDispenserBehavior();
}
