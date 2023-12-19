package minitweaks.mixins.block.grindstone.curses;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin {
    // .filter() lambda in grind
    @SuppressWarnings("target")
    @ModifyExpressionValue(method = "method_16694(Ljava/util/Map$Entry;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isCursed()Z"))
    private static boolean grindCursedFilter(boolean original) {
        // filter out curses too
        return original && !MiniTweaksSettings.removableCurses;
    }
}
