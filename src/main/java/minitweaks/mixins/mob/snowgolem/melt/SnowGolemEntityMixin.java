package minitweaks.mixins.mob.snowgolem.melt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.passive.SnowGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin {
    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/WorldEnvironmentAttributeAccess;getAttributeValue(Lnet/minecraft/world/attribute/EnvironmentAttribute;Lnet/minecraft/util/math/Vec3d;)Ljava/lang/Object;"))
    private Object isHotRedirect(Object original) {
        return (Boolean) original && !MiniTweaksSettings.noSnowGolemMelting;
    }
}
