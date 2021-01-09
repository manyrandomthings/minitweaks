package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntity_GetExperienceMixin {
    @Inject(method = "getCurrentExperience", at = @At(value = "RETURN", ordinal = 2), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void modifyDropCount(PlayerEntity player, CallbackInfoReturnable<Integer> cir, int i) {
        // if larger than max, return max, otherwise return original
        cir.setReturnValue(i > MiniTweaksSettings.maxPlayerXpDrop ? MiniTweaksSettings.maxPlayerXpDrop : i);
    }
}
