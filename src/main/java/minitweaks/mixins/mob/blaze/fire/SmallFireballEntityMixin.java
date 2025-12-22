package minitweaks.mixins.mob.blaze.fire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.SmallFireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmallFireballEntity.class)
public abstract class SmallFireballEntityMixin {
    @ModifyExpressionValue(method = "onBlockHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/rule/GameRules;getValue(Lnet/minecraft/world/rule/GameRule;)Ljava/lang/Object;"))
    private Object preventFire(Object original) {
        return (Boolean) original && !MiniTweaksSettings.disableBlazeFire;
    }
}
