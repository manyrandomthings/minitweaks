package minitweaks.mixins.mob.zombie.convert;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Zombie.class)
public abstract class ZombieEntityMixin {
    @ModifyExpressionValue(method = "killedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getDifficulty()Lnet/minecraft/world/Difficulty;"), expect = 3)
    private Difficulty forceZombieVillager(Difficulty original) {
        return MiniTweaksSettings.villagersAlwaysConvert ? Difficulty.HARD : original;
    }
}
