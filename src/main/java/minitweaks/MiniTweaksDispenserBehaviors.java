package minitweaks;

import minitweaks.dispenser.DyeItemDispenserBehavior;
import minitweaks.dispenser.GoldenAppleDispenserBehavior;
import minitweaks.dispenser.NameTagDispenserBehavior;
import net.minecraft.block.dispenser.DispenserBehavior;

public class MiniTweaksDispenserBehaviors {
    public static DispenserBehavior NAME_TAG = new NameTagDispenserBehavior();
    public static DispenserBehavior DYE_ITEM = new DyeItemDispenserBehavior();
    public static DispenserBehavior GOLDEN_APPLE = new GoldenAppleDispenserBehavior();
}
