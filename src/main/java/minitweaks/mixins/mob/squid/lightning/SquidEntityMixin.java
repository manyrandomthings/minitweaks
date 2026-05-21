package minitweaks.mixins.mob.squid.lightning;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.mob.all.lightning.EntityMixin;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.squid.Squid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Squid.class)
public abstract class SquidEntityMixin extends EntityMixin {

    @Override
    protected void lightningStrikeInject(ServerLevel world, LightningBolt lightning, CallbackInfo ci) {
        if(MiniTweaksSettings.lightningGlowifiesSquids) {
            Squid squid = (Squid) (Object) this;
            squid.convertTo(EntityType.GLOW_SQUID, ConversionParams.single(squid, true, true), (glowSquid) -> {});

            ci.cancel();
        }
    }
}
