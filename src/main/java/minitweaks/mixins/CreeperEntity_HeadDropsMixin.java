package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.CreeperEntity;

@Mixin(CreeperEntity.class)
public class CreeperEntity_HeadDropsMixin {
    @Inject(method = "shouldDropHead", at = @At("HEAD"), cancellable = true)
    private void allHeadsDrop(CallbackInfoReturnable<Boolean> cir) {
        if(((CreeperEntity) (Object) this).shouldRenderOverlay() && MiniTweaksSettings.allChargedCreeperHeadsDrop) {
            cir.setReturnValue(true);
        }
    }
}
