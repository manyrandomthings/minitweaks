package minitweaks.mixins.mob.creeper.head_drops;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    @ModifyExpressionValue(method = "onKilledOther", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/mob/CreeperEntity;headsDropped:Z"))
    private boolean dropHeads(boolean original) {
        return original && !MiniTweaksSettings.allChargedCreeperHeadsDrop;
    }
}
