package minitweaks.mixins.block.grindstone.curses;

import minitweaks.MiniTweaksSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map.Entry;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin {
    // .filter() lambda in grind
    @SuppressWarnings("target")
    @Inject(method = "method_16694(Ljava/util/Map$Entry;)Z", at = @At("HEAD"), cancellable = true)
    private static void grindCursedFilter(Entry<Enchantment, Integer> entry, CallbackInfoReturnable<Boolean> cir) {
        // filter out curses too
        if(MiniTweaksSettings.removableCurses) {
            cir.setReturnValue(false);
        }
    }
}
