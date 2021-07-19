package minitweaks;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.RuleCategory;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class MiniTweaksSettings {
    public enum BlockBreakingType {
        DEFAULT, NONE, BREAK, DESTROY;

        public DestructionType getExplosionType(DestructionType original) {
            switch(this) {
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

    // seed command
    @Rule(
        desc = "Permission level required to use /seed",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.COMMAND}
    )
    public static String commandSeed = "ops";

    @Rule(
        desc = "Mobs require complete darkness (light level 0) to spawn",
        extra = "From 1.18. May change.",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, MiniTweaksRuleCategory.BACKPORT}
    )
    public static boolean completeDarknessSpawning = false;

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
    public static BlockBreakingType creeperBlockDamage = BlockBreakingType.DEFAULT;

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
        desc = "Disables fire made from blaze fireballs",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean disableBlazeFire = false;

    // ghast fireball fire
    @Rule(
        desc = "Disables fire made from ghast fireballs",
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

    // dispensers repair iron golems
    @Rule(
        desc = "Dispensers can repair Iron Golems with iron ingots",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.DISPENSER}
    )
    public static boolean dispensersRepairGolems = false;

    // dragon block breaking
    @Rule(
        desc = "Set dragon block damage breaking type, regardless of mobGriefing gamerule",
        extra = {
            "default: default block breaking",
            "none: no blocks are broken",
            "break: broken blocks are dropped",
            "destroy: broken blocks are destroyed and not dropped"
        },
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static BlockBreakingType dragonBlockDamage = BlockBreakingType.DEFAULT;

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
    public static BlockBreakingType ghastBlockDamage = BlockBreakingType.DEFAULT;

    // infinity+mending stacking
    @Rule(
        desc = "Allows infinity and mending to stack on bows, like in 1.9 to 1.11",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean infinityMendingStacking = false;

    // max xp drop
    @Rule(
        desc = "Maximum amount of xp players drop on death",
        options = {"0", "100", "1000", "10000"},
        validate = Validator.NONNEGATIVE_NUMBER.class,
        strict = false,
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static int maxPlayerXpDrop = 100;

    // mobs drop name tag
    @Rule(
        desc = "Named mobs drop their name tag on death",
        extra = "Note: mobs will drop a name tag even if they weren't named with one",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean mobsDropNametag = false;

    // moveable waterlogged blocks
    @Rule(
        desc = "Waterlogged blocks stay waterlogged when moved with a piston",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.FEATURE}
    )
    public static boolean moveableWaterloggedBlocks = false;

    // farmland feather falling
    @Rule(
        desc = "Prevents farmland from being trampled if you have feather falling",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static boolean noFeatherFallingTrample = false;

    // no repair cost
    @Rule(
        desc = "Removes additional cost for using an item in an anvil multiple times",
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
        desc = "Allows all the different protection types to stack on the same piece of armor, like in 1.14 to 1.14.2",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean protectionStacking = false;

    // quick harvesting
    @Rule(
        desc = "Right click crops with a hoe to harvest and replant",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL, RuleCategory.FEATURE}
    )
    public static boolean quickHarvesting = false;

    // removable curses
    @Rule(
        desc = "Curses are also removed when using grindstones or repair crafting",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean removableCurses = false;

    // dragon egg placing
    @Rule(
        desc = "Dragon eggs will always be placed on the portal after defeating the dragon",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean renewableDragonEgg = false;

    // shave snow layers
    @Rule(
        desc = "Snow layers can be shaved, removing one layer, when right clicked with a shovel",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL, RuleCategory.FEATURE}
    )
    public static boolean shaveSnowLayers = false;

    // slime looting
    @Rule(
        desc = "Bigger slimes spawn more smaller slimes when killed with looting",
        extra = "Additional slimes can be up to as many levels of looting as you have (up to +3 with looting 3, etc)",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, MiniTweaksRuleCategory.ENCHANTMENT, RuleCategory.SURVIVAL}
    )
    public static boolean slimeLooting = false;

    // vex nerf
    @Rule(
        desc = "Vexes will start to die after the evoker that summoned them dies",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean vexesNerf = false;

    @Rule(
        desc = "Villagers will always convert to Zombie Villagers when killed by a zombie, even on easy and normal",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean villagersAlwaysConvert = false;

    // villagers explode beds
    @Rule(
        desc = "Villagers cause explosions when trying to use beds in the nether or end, like players",
        extra = "\"But why?\" Idk, it's just a funny idea I had",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS}
    )
    public static boolean villagersExplodeBeds = false;
}
