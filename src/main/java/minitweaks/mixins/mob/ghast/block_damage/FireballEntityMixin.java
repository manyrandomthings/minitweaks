package minitweaks.mixins.mob.ghast.block_damage;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World.ExplosionSourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FireballEntity.class)
public abstract class FireballEntityMixin {
    @ModifyArg(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private ExplosionSourceType modifyDestructionType(ExplosionSourceType original) {
        return MiniTweaksSettings.noGhastBlockBreaking ? ExplosionSourceType.NONE : original;
    }

    @ModifyArg(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private boolean modifyCreateFire(boolean createFire) {
        return createFire && !MiniTweaksSettings.disableGhastFire;
    }
}
