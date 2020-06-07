package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;

@Mixin(EnderDragonFight.class)
public class EnderDragonFight_DragonEggMixin {
    @Shadow
    boolean previouslyKilled;

    @Inject(method = "dragonKilled", at = @At("HEAD"))
    private void setPreviouslyKilled(EnderDragonEntity dragon, CallbackInfo ci) {
        if(MiniTweaksSettings.renewableDragonEgg) {
            this.previouslyKilled = false;
        }
    }
}
