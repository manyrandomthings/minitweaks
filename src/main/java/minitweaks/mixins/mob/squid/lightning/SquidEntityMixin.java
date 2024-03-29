package minitweaks.mixins.mob.squid.lightning;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.mob.all.lightning.EntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SquidEntity.class)
public abstract class SquidEntityMixin extends EntityMixin {

    @Override
    protected void lightningStrikeInject(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if(MiniTweaksSettings.lightningGlowifiesSquids) {
            SquidEntity squid = (SquidEntity) (Object) this;
            GlowSquidEntity glowSquidEntity = EntityType.GLOW_SQUID.create(world);
            glowSquidEntity.refreshPositionAndAngles(squid.getX(), squid.getY(), squid.getZ(), squid.getYaw(), squid.getPitch());
            glowSquidEntity.setAiDisabled(squid.isAiDisabled());
            if (squid.hasCustomName()) {
                glowSquidEntity.setCustomName(squid.getCustomName());
                glowSquidEntity.setCustomNameVisible(squid.isCustomNameVisible());
            }
            world.spawnEntity(glowSquidEntity);
            squid.discard();

            ci.cancel();
        }
    }
}
