package minitweaks.mixins.mob.dragon.egg;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {
    @ModifyExpressionValue(method = "dragonKilled", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonFight;previouslyKilled:Z", ordinal = 0))
    private boolean shouldGenerateEgg(boolean original) {
        return original && !MiniTweaksSettings.renewableDragonEgg;
    }
}
