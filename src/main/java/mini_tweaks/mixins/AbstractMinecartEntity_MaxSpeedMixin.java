package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntity_MaxSpeedMixin {
    @Inject(method = "getMaxOffRailSpeed", at = @At("RETURN"), cancellable = true)
    public void multiplyMinecartSpeed(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(cir.getReturnValue() * MiniTweaksSettings.minecartSpeedMultiplier);
    }
}
