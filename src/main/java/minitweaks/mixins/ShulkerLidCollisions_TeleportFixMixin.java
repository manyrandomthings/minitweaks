package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.ShulkerLidCollisions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

@Mixin(ShulkerLidCollisions.class)
public abstract class ShulkerLidCollisions_TeleportFixMixin {
    @Inject(method = "getLidCollisionBox", at = @At("RETURN"), cancellable = true)
    private static void thing(BlockPos pos, Direction direction, CallbackInfoReturnable<Box> cir) {
        if(MiniTweaksSettings.shulkerPortalFix) {
            // fixes MC-183884 by making the box that checks for entities inside very slightly smaller, so it doesn't get neighbor shulkers
            Box original = cir.getReturnValue();
            cir.setReturnValue(original.contract(1.0E-6D));
        }
    }
}
