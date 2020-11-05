package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntity_BulletCloneMixin extends GolemEntity {
    protected ShulkerEntity_BulletCloneMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract boolean isClosed();

    @Shadow
    public abstract boolean tryTeleport();

    // shulker cloning from 20w45a
    @Inject(method = "damage", at = @At("RETURN"), cancellable = true)
    private void cloneShulker(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // check if return value is true and rule is
        if(cir.getReturnValue() && MiniTweaksSettings.shulkerCloning && source.isProjectile()) {
            Entity sourceEntity = source.getSource();
            if(sourceEntity != null && sourceEntity.getType() == EntityType.SHULKER_BULLET) {
                this.spawnClone();
            }
        }
    }

    private void spawnClone() {
        Vec3d pos = this.getPos();
        Box box = this.getBoundingBox();
        // if shulker is open, try to teleport, and spawn new shulker if successful
        if(!this.isClosed() && this.tryTeleport()) {
            // get odds of successfully cloning based on
            int shulkersCount = this.world.getEntitiesByType(EntityType.SHULKER, box.expand(8.0D), Entity::isAlive).size();
            float cloneOdds = (float) (shulkersCount - 1) / 5.0F;
            if(cloneOdds <= this.world.random.nextFloat()) {
                ShulkerEntity shulkerEntity = EntityType.SHULKER.create(this.world);
                shulkerEntity.refreshPositionAfterTeleport(pos);
                this.world.spawnEntity(shulkerEntity);
            }
        }
    }
}
