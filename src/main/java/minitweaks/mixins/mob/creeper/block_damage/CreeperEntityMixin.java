package minitweaks.mixins.mob.creeper.block_damage;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World.ExplosionSourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CreeperEntity.class, priority = 1001)
public abstract class CreeperEntityMixin {
    @ModifyExpressionValue(method = "explode", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World$ExplosionSourceType;MOB:Lnet/minecraft/world/World$ExplosionSourceType;"))
    private ExplosionSourceType modifiedExplode(ExplosionSourceType original) {
        return MiniTweaksSettings.noCreeperBlockBreaking ? ExplosionSourceType.NONE : original;
    }
}
