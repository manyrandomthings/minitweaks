package minitweaks.mixins.mob.blaze.fire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmallFireball.class)
public abstract class SmallFireballEntityMixin {
    @ModifyExpressionValue(method = "onHitBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object preventFire(Object original) {
        return (Boolean) original && !MiniTweaksSettings.disableBlazeFire;
    }
}
