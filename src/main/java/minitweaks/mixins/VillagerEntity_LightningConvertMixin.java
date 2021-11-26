package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntity_LightningConvertMixin extends MerchantEntity {
    public VillagerEntity_LightningConvertMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void lightningWitchConversion(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if(MiniTweaksSettings.noVillagerWitchConversion) {
            super.onStruckByLightning(world, lightning);
            ci.cancel();
        }
    }
}
