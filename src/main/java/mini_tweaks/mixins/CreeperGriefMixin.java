package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.explosion.Explosion.DestructionType;

@Mixin(CreeperEntity.class)
public class CreeperGriefMixin {
    @ModifyArg(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private DestructionType modifiedExplode(DestructionType original) {
        return MiniTweaksSettings.getExplosionType(MiniTweaksSettings.creeperBlockDamage, original);
    }
}
