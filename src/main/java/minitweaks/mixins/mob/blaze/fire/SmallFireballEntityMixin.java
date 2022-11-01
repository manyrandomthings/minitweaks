package minitweaks.mixins.mob.blaze.fire;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallFireballEntity.class)
public abstract class SmallFireballEntityMixin {
    @Inject(method = "onBlockHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"), cancellable = true)
    private void preventFire(BlockHitResult blockHitResult, CallbackInfo ci) {
        if(MiniTweaksSettings.disableBlazeFire) {
            // cancel if blaze fire is disabled
            ci.cancel();
        }
    }
}
