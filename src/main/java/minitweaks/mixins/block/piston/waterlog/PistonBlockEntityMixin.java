package minitweaks.mixins.block.piston.waterlog;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.block.entity.PistonBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonBlockEntity.class)
public abstract class PistonBlockEntityMixin {
    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Boolean;booleanValue()Z"))
    private static boolean checkWaterloggedState(boolean original) {
        return original && !MiniTweaksSettings.moveableWaterloggedBlocks;
    }
}
