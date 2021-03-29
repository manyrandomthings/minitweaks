package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;

@Mixin(EnderDragonFight.class)
public class EnderDragonFight_DragonEggMixin {
    @Shadow
    private boolean previouslyKilled;

    @Inject(method = "dragonKilled", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonFight;previouslyKilled:Z", ordinal = 0, shift = At.Shift.BEFORE))
    private void setPreviouslyKilled(EnderDragonEntity dragon, CallbackInfo ci) {
        if(MiniTweaksSettings.renewableDragonEgg) {
            this.previouslyKilled = false;
        }
    }
}
