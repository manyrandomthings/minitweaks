package minitweaks.mixins.mob.creeper.block_damage;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.explosion.Explosion.DestructionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = CreeperEntity.class, priority = 1001)
public abstract class CreeperEntityMixin {
    @ModifyArg(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private DestructionType modifiedExplode(DestructionType original) {
        return MiniTweaksSettings.creeperBlockDamage.getExplosionType(original);
    }
}
