package minitweaks;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;
import carpet.api.settings.Validator;
import carpet.api.settings.Validators;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;

public class MiniTweaksSettings {
    public enum BlockBreakingType {
        DEFAULT, NONE, BREAK, DESTROY;
    }

    public enum ItemPickupType {
        DEFAULT, ALWAYS, NEVER;
    }

    private static class ServerSideOnlyRuleCondition implements Rule.Condition {
        @Override
        public boolean shouldRegister() {
            return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
        }
    }

    private static class ItemDespawnTimeValidator extends Validator<Integer> {
        @Override
        public Integer validate(ServerCommandSource source, CarpetRule<Integer> currentRule, Integer newValue, String typedString) {
            if(newValue >= -1 && newValue <= 32) {
                return newValue;
            }
            return null;
        }
    }

    // all charged creeper heads drop
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean allChargedCreeperHeadsDrop;

    // seed command
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.COMMAND},
        conditions = ServerSideOnlyRuleCondition.class
    )
    public static String commandSeed = "ops";

    // death items despawn time
    @Rule(
        options = {"5", "10", "15", "30", "-1"},
        validators = ItemDespawnTimeValidator.class,
        strict = false,
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static int deathItemsDespawnMinutes = 5;

    // blaze fireball fire
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean disableBlazeFire = false;

    // ghast fireball fire
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean disableGhastFire = false;

    // dispensers bucket mobs
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersBucketMobs = false;

    // dispensers cure villagers
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER, RuleCategory.SURVIVAL}
    )
    public static boolean dispensersCureVillagers = false;

    // dispensers duplicate allays
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersDuplicateAllays = false;

    // dispensers dye mobs
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersDyeMobs = false;

    // dispensers use name tags
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersNameMobs = false;

    // dispensers repair iron golems
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersRepairGolems = false;

    // dragon block breaking
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static BlockBreakingType dragonBlockDamage = BlockBreakingType.DEFAULT;

    // dye sheared sheep
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean dyeableShearedSheep = false;

    // dye shulkers
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean dyeableShulkers = false;

    // echo shards enable sculk shriekers
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.FEATURE}
    )
    public static boolean echoShardsEnableShriekers = false;

    // faster oxidation
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL, RuleCategory.FEATURE}
    )
    public static boolean fasterOxidation = false;

    // infinity+mending stacking
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean infinityMendingStacking = false;

    // lightning glowifies squids
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean lightningGlowifiesSquids = false;

    // max xp drop
    @Rule(
        options = {"0", "100", "1000", "10000"},
        validators = Validators.NonNegativeNumber.class,
        strict = false,
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static int maxPlayerXpDrop = 100;

    // mob item pickup
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static ItemPickupType mobItemPickup = ItemPickupType.DEFAULT;

    // mobs drop name tag
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean mobsDropNametag = false;

    // moveable waterlogged blocks
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.FEATURE}
    )
    public static boolean moveableWaterloggedBlocks = false;

    // no creeper block breaking
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean noCreeperBlockBreaking = false;

    // farmland feather falling
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static boolean noFeatherFallingTrample = false;

    // no ghast fireball block breaking
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean noGhastBlockBreaking = false;

    // no repair cost
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static boolean noRepairCost = false;

    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean noVillagerWitchConversion = false;

    // phantom spawning
    @Rule(
        options = {"72000", "360000", "720000"},
        validators = Validators.NonNegativeNumber.class,
        strict = false,
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static int phantomSpawningTime = 72000;

    // protection stacking
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean protectionStacking = false;

    // quick harvesting
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL, RuleCategory.FEATURE}
    )
    public static boolean quickHarvesting = false;

    // removable curses
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean removableCurses = false;

    // dragon egg placing
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean renewableDragonEgg = false;

    // renewable raw ores
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL, RuleCategory.FEATURE}
    )
    public static boolean renewableRawOres = false;

    // shave snow layers
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL, RuleCategory.FEATURE}
    )
    public static boolean shaveSnowLayers = false;

    // slime looting
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean slimeLooting = false;

    // vex nerf
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean vexesNerf = false;

    // villagers always convert
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean villagersAlwaysConvert = false;

    // villagers explode beds
    @Rule(
        categories = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean villagersExplodeBeds = false;
}
