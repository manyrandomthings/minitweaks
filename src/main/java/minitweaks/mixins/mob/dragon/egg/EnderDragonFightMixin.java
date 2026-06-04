package minitweaks.mixins.mob.dragon.egg;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {
    @ModifyExpressionValue(method = "setDragonKilled", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/end/EnderDragonFight;hasPreviouslyKilledDragon:Z", ordinal = 0, opcode = Opcodes.GETFIELD))
    private boolean shouldGenerateEgg(boolean original) {
        return original && !MiniTweaksSettings.renewableDragonEgg;
    }
}
