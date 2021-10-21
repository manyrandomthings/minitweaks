package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;

@Mixin(value = ZombieEntity.class, priority = 1001)
public class ZombieEntity_AlwaysConvertMixin {
    @Redirect(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"), expect = 3, require = 0)
    private Difficulty forceZombieVillager(ServerWorld world) {
        if(MiniTweaksSettings.villagersAlwaysConvert) {
            return Difficulty.HARD;
        }
        return world.getDifficulty();
    }
}
