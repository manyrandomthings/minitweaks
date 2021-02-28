package minitweaks.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import minitweaks.ShulkerEntityColorHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
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
            // get odds of successfully cloning based on how many shulkers are within 8 blocks (17x17x17 area)
            int shulkersCount = this.world.getEntitiesByType(EntityType.SHULKER, box.expand(8.0D), Entity::isAlive).size();
            float cloneOdds = (float) (shulkersCount - 1) / 5.0F;
            if(cloneOdds <= this.world.random.nextFloat()) {
                ShulkerEntity shulkerEntity = EntityType.SHULKER.create(this.world);
                // replace with this.getColor() in 1.17
                DyeColor dyeColor = ShulkerEntityColorHelper.getColor((ShulkerEntity) (Object) this);
                if(dyeColor != null) {
                    // replace with shulkerEntity.setColor(dyeColor) in 1.17
                    ShulkerEntityColorHelper.setColor(shulkerEntity, dyeColor);
                }


                shulkerEntity.refreshPositionAfterTeleport(pos);
                this.world.spawnEntity(shulkerEntity);
            }
        }
    }

    // bad hack but it works, I guess
    @Override
    public void copyFrom(Entity original) {
        super.copyFrom(original);

        if(MiniTweaksSettings.shulkerPortalFix) {
            // reset attached face and attached block to default values
            // fixes MC-139265, MC-168900 (probably)
            this.dataTracker.set(ShulkerEntity_TrackerKeysAccessorMixin.getAttachedFaceTrackerKey(), Direction.DOWN);
            this.dataTracker.set(ShulkerEntity_TrackerKeysAccessorMixin.getAttachedBlockTrackerKey(), Optional.empty());
        }
    }
}
