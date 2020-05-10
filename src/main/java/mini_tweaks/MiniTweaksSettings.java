package mini_tweaks;

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
        // No blocks broken
        if(option == ExplosionType.NONE) {
            return DestructionType.NONE;
        }
        // Blocks are broken but all are dropped (like tnt)
        else if(option == ExplosionType.BREAK) {
            return DestructionType.BREAK;
        }
        // Blocks are broken and some are destroyed (like default creepers)
        else if(option == ExplosionType.DESTROY) {
            return DestructionType.DESTROY;
        }
        return original;
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

    // ghast fireball fire
    @Rule(
        desc = "Disable random fire from ghast fireballs",
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static boolean disableGhastFire = false;

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

    // phantom spawning
    @Rule(
        desc = "Amount of ticks before Phantoms start having a chance to spawn",
        options = {"72000", "360000", "720000"},
        validate = Validator.NONNEGATIVE_NUMBER.class,
        strict = false,
        category = {MiniTweaksRuleCategory.MODNAME, MiniTweaksRuleCategory.MOBS, RuleCategory.SURVIVAL}
    )
    public static int phantomSpawningTime = 72000;

    // dragon egg placing
    @Rule(
        desc = "Dragon eggs will always be placed on the portal after defeating the dragon",
        category = {MiniTweaksRuleCategory.MODNAME, RuleCategory.SURVIVAL}
    )
    public static boolean renewableDragonEgg = false;
}
