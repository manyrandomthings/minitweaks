package minitweaks.mixins.mob.dragon.block_damage;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import minitweaks.MiniTweaksSettings;
import minitweaks.MiniTweaksSettings.BlockBreakingType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderDragon.class)
public abstract class EnderDragonEntityMixin {
    @ModifyExpressionValue(method = "checkWalls", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object gameruleCheck(Object original) {
        return switch(MiniTweaksSettings.dragonBlockDamage) {
            case NONE -> false;
            case BREAK, DESTROY -> true;
            case DEFAULT -> original;
        };
    }

    @WrapOperation(method = "checkWalls", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    private boolean destroyType(ServerLevel world, BlockPos pos, boolean move, Operation<Boolean> original) {
        if(MiniTweaksSettings.dragonBlockDamage == BlockBreakingType.BREAK) {
            // break block and drop as item
            return world.destroyBlock(pos, true, (EnderDragon) (Object) this);
        }

        // default block removal
        return original.call(world, pos, move);
    }
}
