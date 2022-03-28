package minitweaks.mixins.mob.zombie.convert;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ZombieEntity.class, priority = 999) //todo: change to 1001
public abstract class ZombieEntityMixin {
    @Redirect(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"), expect = 3, require = 0)
    private Difficulty forceZombieVillager(ServerWorld world) {
        if(MiniTweaksSettings.villagersAlwaysConvert) {
            return Difficulty.HARD;
        }
        return world.getDifficulty();
    }
}
