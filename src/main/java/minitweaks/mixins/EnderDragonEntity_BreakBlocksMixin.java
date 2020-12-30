package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import minitweaks.MiniTweaksSettings;
import minitweaks.MiniTweaksSettings.BlockBreakingType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntity_BreakBlocksMixin {
    @Redirect(method = "destroyBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean gameruleCheck(GameRules gamerules, GameRules.Key<GameRules.BooleanRule> gameruleKey) {
        // if block breaking type is not none, otherwise return gamerule value
        if(MiniTweaksSettings.dragonBlockDamage != BlockBreakingType.DEFAULT) {
            // if block breaking type is none, return gamerule value as false
            return MiniTweaksSettings.dragonBlockDamage != BlockBreakingType.NONE;
        }
        return gamerules.getBoolean(gameruleKey);
    }

    @Redirect(method = "destroyBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean destroyType(World world, BlockPos pos, boolean move) {
        // this mixin shouldn't run if block breaking type is none
        // if block breaking type is not break, remove block
        if(MiniTweaksSettings.dragonBlockDamage != BlockBreakingType.BREAK) {
            return world.removeBlock(pos, move);
        }
        // break block and drop as item
        return world.breakBlock(pos, true, (EnderDragonEntity) (Object) this);
    }
}
