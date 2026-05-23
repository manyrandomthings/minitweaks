package minitweaks.mixins.mob.creeper.block_damage;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Creeper.class)
public abstract class CreeperMixin {
    @ModifyExpressionValue(method = "explodeCreeper", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;MOB:Lnet/minecraft/world/level/Level$ExplosionInteraction;"))
    private ExplosionInteraction modifiedExplode(ExplosionInteraction original) {
        return MiniTweaksSettings.noCreeperBlockBreaking ? ExplosionInteraction.NONE : original;
    }
}
