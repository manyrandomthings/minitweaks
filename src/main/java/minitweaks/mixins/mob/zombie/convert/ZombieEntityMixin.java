package minitweaks.mixins.mob.zombie.convert;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin {
    @ModifyExpressionValue(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"), expect = 3)
    private Difficulty forceZombieVillager(Difficulty original) {
        return MiniTweaksSettings.villagersAlwaysConvert ? Difficulty.HARD : original;
    }
}
