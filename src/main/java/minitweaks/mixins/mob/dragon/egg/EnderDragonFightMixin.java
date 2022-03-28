package minitweaks.mixins.mob.dragon.egg;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {
    @Shadow
    private boolean previouslyKilled;

    @Inject(method = "dragonKilled", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonFight;previouslyKilled:Z", ordinal = 0))
    private void setPreviouslyKilled(EnderDragonEntity dragon, CallbackInfo ci) {
        if(MiniTweaksSettings.renewableDragonEgg) {
            this.previouslyKilled = false;
        }
    }
}
