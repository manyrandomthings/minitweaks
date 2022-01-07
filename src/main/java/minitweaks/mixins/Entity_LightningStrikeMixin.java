package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;

@Mixin(Entity.class)
public abstract class Entity_LightningStrikeMixin {
    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    protected void lightningStrikeInject(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        // blank
    }
}
