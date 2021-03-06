package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.hit.BlockHitResult;

@Mixin(SmallFireballEntity.class)
public abstract class SmallFireballEntity_FireMixin {
    @Inject(method = "onBlockHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/AbstractFireballEntity;onBlockHit(Lnet/minecraft/util/hit/BlockHitResult;)V", shift = At.Shift.AFTER), cancellable = true)
    private void preventFire(BlockHitResult blockHitResult, CallbackInfo ci) {
        if(MiniTweaksSettings.disableBlazeFire) {
            // cancel if blaze fire is disabled
            ci.cancel();
        }
    }
}
