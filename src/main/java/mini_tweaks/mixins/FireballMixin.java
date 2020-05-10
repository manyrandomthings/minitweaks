package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.Explosion.DestructionType;

@Mixin(FireballEntity.class)
public class FireballMixin {
    @Redirect(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion modifyFireballExplosion(World world, Entity entity, double x, double y, double z, float power, boolean createFire, DestructionType destructionType) {
        // get disableGhastFire option for disabling fire
        boolean modifiedCreateFire = !MiniTweaksSettings.disableGhastFire;
        // get destruction type for ghastFireballBlockDamage
        DestructionType modifiedDestructionType = MiniTweaksSettings.getExplosionType(MiniTweaksSettings.ghastBlockDamage, destructionType);
        return world.createExplosion(entity, x, y, z, power, modifiedCreateFire, modifiedDestructionType);
    }
}
