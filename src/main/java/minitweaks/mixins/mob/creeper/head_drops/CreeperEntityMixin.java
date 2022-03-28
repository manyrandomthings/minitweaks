package minitweaks.mixins.mob.creeper.head_drops;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    @Inject(method = "shouldDropHead", at = @At("HEAD"), cancellable = true)
    private void allHeadsDrop(CallbackInfoReturnable<Boolean> cir) {
        if(((CreeperEntity) (Object) this).shouldRenderOverlay() && MiniTweaksSettings.allChargedCreeperHeadsDrop) {
            cir.setReturnValue(true);
        }
    }
}
