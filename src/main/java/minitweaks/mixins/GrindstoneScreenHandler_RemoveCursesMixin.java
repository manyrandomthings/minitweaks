package minitweaks.mixins;

import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.GrindstoneScreenHandler;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandler_RemoveCursesMixin {
    // .filter() lambda in grind
    @SuppressWarnings("target")
    @Inject(method = "method_16694", at = @At("HEAD"), cancellable = true)
    private static void grindCursedFilter(Entry<Enchantment, Integer> entry, CallbackInfoReturnable<Boolean> cir) {
        // filter out curses too
        if(MiniTweaksSettings.removableCurses) {
            cir.setReturnValue(false);
        }
    }
}
