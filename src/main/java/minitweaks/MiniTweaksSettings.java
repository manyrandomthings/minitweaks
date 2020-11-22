package minitweaks;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.RuleCategory;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class MiniTweaksSettings {
    public enum ExplosionType {
        DEFAULT, NONE, BREAK, DESTROY
    }

    public static DestructionType getExplosionType(ExplosionType option, DestructionType original) {
        switch(option) {
            // No blocks broken
            case NONE:
                return DestructionType.NONE;

            // Blocks are broken but all are dropped (like tnt)
            case BREAK:
                return DestructionType.BREAK;

            // Blocks are broken and some are destroyed (like default creepers)
            case DESTROY:
                return DestructionType.DESTROY;

            // Default explosion (no modification)
            case DEFAULT:
            default:
                return original;
        }
    }

    private static class ItemDespawnTimeValidator extends Validator<Integer> {
        @Override
        public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String typedString) {
            if(newValue >= -1 && newValue <= 32) {
                return newValue;
            }
            return null;
        }
    }

    private static class MinecartSpeedMultiplierValidator extends Validator<Double> {
        @Override
        public Double validate(ServerCommandSource source, ParsedRule<Double> currentRule, Double newValue, String typedString) {
            if(newValue >= 0.1 && newValue <= 20.0) {
                return newValue;
            }
            return null;
        }
    }

    // creeper block breaking
    @Rule(
        desc = "Set creeper explosion block damage type, regardless of mobGriefing gamerule",
        extra = {
            "default: uses default explosion",
            "none: no blocks broken",
            "break: all broken blocks are dropped (like tnt)",
            "destroy: broken blocks are sometimes dropped (like default creepers)"
        },
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static ExplosionType creeperBlockDamage = ExplosionType.DEFAULT;

    // death items despawn time
    @Rule(
        desc = "How many minutes it takes for a player's items to despawn after death",
        extra = "-1 for infinte, 0 for instant despawn",
        options = {"5", "10", "15", "30", "-1"},
        validate = ItemDespawnTimeValidator.class,
        strict = false,
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static int deathItemsDespawnMinutes = 5;

    // blaze fireball fire
    @Rule(
        desc = "Disable fires made from blaze fireballs",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean disableBlazeFire = false;

    // ghast fireball fire
    @Rule(
        desc = "Disable random fire from ghast fireballs",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean disableGhastFire = false;

    // dispensers cure villagers
    @Rule(
        desc = "Dispensers feed golden apples to zombie villagers with weakness",
        extra = {
            "Note: dispensers curing a villager does not lower the",
            "villager's prices due to gossips being player-specific"
        },
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER, RuleCategory.SURVIVAL}
    )
    public static boolean dispensersCureVillagers = false;

    // dispensers dye mobs
    @Rule(
        desc = "Dispensers can dye sheep (and shulkers if dyeableShulkers is enabled)",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersDyeMobs = false;

    // dispensers use name tags
    @Rule(
        desc = "Dispensers use name tags on mobs",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersNameMobs = false;

    // dispensers use cauldrons
    @Rule(
        desc = "Dispensers use cauldrons",
        extra = {
            "When facing into a cauldron, dispensers can",
            "fill/empty buckets and bottles, remove layers",
            "from banners, and undye leather armor or shulker boxes"
        },
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL, RuleCategory.DISPENSER}
    )
    public static boolean dispensersUseCauldrons = false;

    // dye shulkers
    @Rule(
        desc = "Shulkers can be dyed",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean dyeableShulkers = false;

    // ghast fireball block breaking
    @Rule(
        desc = "Set ghast explosion block damage type, regardless of mobGriefing gamerule",
        extra = {
            "default: uses default explosion",
            "none: no blocks broken",
            "break: all broken blocks are dropped (like tnt)",
            "destroy: broken blocks are sometimes dropped (like default creepers)"
        },
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static ExplosionType ghastBlockDamage = ExplosionType.DEFAULT;

    // infinity+mending stacking
    @Rule(
        desc = "Allows infinity and mending to stack on bows",
        extra = "Like 1.9-1.11",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean infinityMendingStacking = false;

    // minecart speed multiplier
    @Rule(
        desc = "Minecart speed multiplier",
        extra = {
            "Allows maximum speed for minecarts to be increased/decreased",
            "Default max speed is 0.4 blocks per tick (8 blocks/sec)",
            "New max speed is (0.4 * value) blocks per tick",
            "This is experimental and may cause issues, such as",
            "derailing at high speeds around corners, and stopping at upwards slopes.",
            "Must be between 0.1 and 20.0"
        },
        options = {"1.0", "2.0", "5.0", "10.0", "20.0"},
        validate = MinecartSpeedMultiplierValidator.class,
        strict = false,
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.EXPERIMENTAL}
    )
    public static double minecartSpeedMultiplier = 1.0;

    // minecart speed multiplier passengers
    @Rule(
        desc = "Should minecartSpeedMultiplier rule only apply to minecarts with a passenger",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.EXPERIMENTAL}
    )
    public static boolean minecartSpeedMultiplierPassengersOnly = false;

    // mobs drop name tag
    @Rule(
        desc = "Named mobs drop their name tag on death",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean mobsDropNametag = false;

    // farmland feather falling
    @Rule(
        desc = "Prevents farmland from being trampled if you have feather falling on",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static boolean noFeatherFallingTrample = false;

    // no repair cost
    @Rule(
        desc = "No additional cost for using an item in an anvil multiple times",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static boolean noRepairCost = false;

    // phantom spawning
    @Rule(
        desc = "Amount of ticks before Phantoms start having a chance to spawn",
        options = {"72000", "360000", "720000"},
        validate = Validator.NONNEGATIVE_NUMBER.class,
        strict = false,
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static int phantomSpawningTime = 72000;

    // protection stacking
    @Rule(
        desc = "Allows all the different protection types to stack on the same piece of armor",
        extra = "Like enchanting from 1.14-1.14.2",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean protectionStacking = false;

    // dragon egg placing
    @Rule(
        desc = "Dragon eggs will always be placed on the portal after defeating the dragon",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static boolean renewableDragonEgg = false;

    // 1.17 shulker cloning
    @Rule(
        desc = "1.17 Shulker cloning",
        extra = {
            "A shulker hitting a shulker with a shulker bullet can make a new shulker",
            "Feature from 20w45a, subject to change"
        },
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL, RuleCategory.FEATURE}
    )
    public static boolean shulkerCloning = false;

    // slime looting
    @Rule(
        desc = "Bigger slimes spawn more smaller slimes when killed with looting",
        extra = "Additional slimes can be up to as many levels of looting as you have (up to +3 with looting 3, etc)",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean slimeLooting = false;
}
