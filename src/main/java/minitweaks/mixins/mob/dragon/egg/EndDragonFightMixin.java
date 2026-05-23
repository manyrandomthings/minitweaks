package minitweaks.mixins.mob.dragon.egg;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EndDragonFight.class)
public abstract class EndDragonFightMixin {
    @ModifyExpressionValue(method = "setDragonKilled", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/end/EndDragonFight;previouslyKilled:Z", ordinal = 0))
    private boolean shouldGenerateEgg(boolean original) {
        return original && !MiniTweaksSettings.renewableDragonEgg;
    }
}
