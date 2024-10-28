package minitweaks.mixins.mob.squid.lightning;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.mob.all.lightning.EntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.conversion.EntityConversionContext;
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
            squid.convertTo(EntityType.GLOW_SQUID, EntityConversionContext.create(squid, true, true), (glowSquid) -> {});

            ci.cancel();
        }
    }
}
