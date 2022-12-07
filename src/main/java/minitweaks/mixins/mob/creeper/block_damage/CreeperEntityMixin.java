package minitweaks.mixins.mob.creeper.block_damage;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World.ExplosionSourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = CreeperEntity.class, priority = 1001)
public abstract class CreeperEntityMixin {
    @ModifyArg(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private ExplosionSourceType modifiedExplode(ExplosionSourceType original) {
        return MiniTweaksSettings.noCreeperBlockBreaking ? ExplosionSourceType.NONE : original;
    }
}
