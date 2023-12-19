package minitweaks.mixins.mob.ghast.block_damage;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World.ExplosionSourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireballEntity.class)
public abstract class FireballEntityMixin {
    @ModifyExpressionValue(method = "onCollision", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World$ExplosionSourceType;MOB:Lnet/minecraft/world/World$ExplosionSourceType;"))
    private ExplosionSourceType modifyDestructionType(ExplosionSourceType original) {
        return MiniTweaksSettings.noGhastBlockBreaking ? ExplosionSourceType.NONE : original;
    }

    @ModifyExpressionValue(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean modifyCreateFire(boolean createFire) {
        return createFire && !MiniTweaksSettings.disableGhastFire;
    }
}
