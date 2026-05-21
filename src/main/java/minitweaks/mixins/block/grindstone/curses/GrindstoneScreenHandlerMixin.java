package minitweaks.mixins.block.grindstone.curses;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.inventory.GrindstoneMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrindstoneMenu.class)
public abstract class GrindstoneScreenHandlerMixin {
    // .filter() lambda in grind
    @SuppressWarnings("target")
    @ModifyExpressionValue(method = "method_58073", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Holder;is(Lnet/minecraft/tags/TagKey;)Z"))
    private static boolean grindCursedFilter(boolean original) {
        // filter out curses too
        return original && !MiniTweaksSettings.removableCurses;
    }
}
