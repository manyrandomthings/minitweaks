package minitweaks.mixins.mob.blaze.fire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.SmallFireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmallFireballEntity.class)
public abstract class SmallFireballEntityMixin {
    @ModifyExpressionValue(method = "onBlockHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean preventFire(boolean original) {
        return original && !MiniTweaksSettings.disableBlazeFire;
    }
}
