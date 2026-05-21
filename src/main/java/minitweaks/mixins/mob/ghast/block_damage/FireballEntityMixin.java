package minitweaks.mixins.mob.ghast.block_damage;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LargeFireball.class)
public abstract class FireballEntityMixin {
    @ModifyExpressionValue(method = "onHit", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;MOB:Lnet/minecraft/world/level/Level$ExplosionInteraction;"))
    private ExplosionInteraction modifyDestructionType(ExplosionInteraction original) {
        return MiniTweaksSettings.noGhastBlockBreaking ? ExplosionInteraction.NONE : original;
    }

    @ModifyExpressionValue(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object modifyCreateFire(Object createFire) {
        return (Boolean) createFire && !MiniTweaksSettings.disableGhastFire;
    }
}
