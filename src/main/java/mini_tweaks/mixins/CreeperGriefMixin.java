package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import mini_tweaks.MiniTweaksSettings;
import mini_tweaks.MiniTweaksSettings.CreeperExplosionType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.explosion.Explosion;

@Mixin(CreeperEntity.class)
public class CreeperGriefMixin {
    @ModifyArg(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion.DestructionType modifiedExplode(Explosion.DestructionType original) {
        // No blocks broken
        if(MiniTweaksSettings.creeperBlockDamage == CreeperExplosionType.NONE) {
            return Explosion.DestructionType.NONE;
        }
        // Blocks are broken but all are dropped (like tnt)
        else if(MiniTweaksSettings.creeperBlockDamage == CreeperExplosionType.BREAK) {
            return Explosion.DestructionType.BREAK;
        }
        // Blocks are broken and some are destroyed (like default creepers)
        else if(MiniTweaksSettings.creeperBlockDamage == CreeperExplosionType.DESTROY) {
            return Explosion.DestructionType.DESTROY;
        }
        return original;
    }
}
