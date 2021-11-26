package minitweaks.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;

@Mixin(HostileEntity.class)
public abstract class HostileEntity_SpawnLightLevelMixin {
    @Inject(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;toServerWorld()Lnet/minecraft/server/world/ServerWorld;"), cancellable = true)
    private static void lightLevelModify(ServerWorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if(MiniTweaksSettings.completeDarknessSpawning && world.getLightLevel(LightType.BLOCK, pos) > 0) {
            cir.setReturnValue(false);
        }
    }
}
