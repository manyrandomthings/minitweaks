package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntity_MaxSpeedMixin extends Entity {
    public AbstractMinecartEntity_MaxSpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getMaxOffRailSpeed", at = @At("RETURN"), cancellable = true)
    public void multiplyMinecartSpeed(CallbackInfoReturnable<Double> cir) {
        if(!MiniTweaksSettings.minecartSpeedMultiplierPassengersOnly || this.hasPassengers()) {
            cir.setReturnValue(cir.getReturnValue() * MiniTweaksSettings.minecartSpeedMultiplier);
        }
    }
}
