package minitweaks.mixins;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

@Mixin(SquidEntity.class)
public abstract class SquidEntity_LightningConvertMixin extends WaterCreatureEntity {
    protected SquidEntity_LightningConvertMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Intrinsic
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        super.onStruckByLightning(world, lightning);
    }

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void lightningGlowify(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if(MiniTweaksSettings.lightningGlowifiesSquids) {
            GlowSquidEntity glowSquidEntity = EntityType.GLOW_SQUID.create(world);
            glowSquidEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            glowSquidEntity.setAiDisabled(this.isAiDisabled());
            if (this.hasCustomName()) {
                glowSquidEntity.setCustomName(this.getCustomName());
                glowSquidEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            world.spawnEntity(glowSquidEntity);
            this.discard();

            ci.cancel();
        }
    }
}
