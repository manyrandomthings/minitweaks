package minitweaks.mixins.mob.dragon.block_damage;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import minitweaks.MiniTweaksSettings;
import minitweaks.MiniTweaksSettings.BlockBreakingType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin {
    @ModifyExpressionValue(method = "destroyBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean gameruleCheck(boolean original) {
        return switch(MiniTweaksSettings.dragonBlockDamage) {
            case NONE -> false;
            case BREAK, DESTROY -> true;
            case DEFAULT -> original;
        };
    }

    @WrapOperation(method = "destroyBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean destroyType(World world, BlockPos pos, boolean move, Operation<Boolean> original) {
        if(MiniTweaksSettings.dragonBlockDamage == BlockBreakingType.BREAK) {
            // break block and drop as item
            return world.breakBlock(pos, true, (EnderDragonEntity) (Object) this);
        }

        // default block removal
        return original.call(world, pos, move);
    }
}
