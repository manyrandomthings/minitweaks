package minitweaks.mixins.mob.vex.nerf;

import minitweaks.MiniTweaksSettings;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Vex.class)
public abstract class VexEntityMixin extends Monster {
    protected VexEntityMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Shadow
    abstract Mob getOwner();

    @Shadow
    abstract void setOwner(Mob owner);

    @Shadow
    abstract void setLimitedLife(int lifeTicks);


    @Inject(method = "tick", at = @At("HEAD"))
    private void checkOwnerDeath(CallbackInfo ci) {
        if(MiniTweaksSettings.vexesNerf) {
            Mob owner = this.getOwner();
            // check if owner is dead
            if(owner != null && !owner.isAlive()) {
                // set owner to null so this code doesn't keep running
                this.setOwner(null);
                // set remaining life time to 1 to 5 seconds
                this.setLimitedLife(20 + this.random.nextInt(80));
            }
        }
    }
}
