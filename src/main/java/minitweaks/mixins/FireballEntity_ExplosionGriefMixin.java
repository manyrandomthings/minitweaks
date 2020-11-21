package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.explosion.Explosion.DestructionType;

@Mixin(FireballEntity.class)
public class FireballEntity_ExplosionGriefMixin {
    @ModifyArg(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private DestructionType modifyDestructionType(DestructionType destructionType) {
        return MiniTweaksSettings.getExplosionType(MiniTweaksSettings.ghastBlockDamage, destructionType);
    }

    @ModifyArg(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private boolean modifyCreateFire(boolean createFire) {
        return createFire && !MiniTweaksSettings.disableGhastFire;
    }
}
