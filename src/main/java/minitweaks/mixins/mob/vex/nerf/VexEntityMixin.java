package minitweaks.mixins.mob.vex.nerf;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VexEntity.class)
public abstract class VexEntityMixin extends HostileEntity {
    protected VexEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void checkOwnerDeath(CallbackInfo ci) {
        if(MiniTweaksSettings.vexesNerf) {
            VexEntity self = (VexEntity) (Object) this;
            MobEntity owner = self.getOwner();
            // check if owner is dead
            if(owner != null && !owner.isAlive()) {
                // set owner to null so this code doesn't keep running
                self.setOwner(null);
                // set remaining life time to 1 to 5 seconds
                self.setLifeTicks(20 + this.random.nextInt(80));
            }
        }
    }
}
