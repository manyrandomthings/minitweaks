package minitweaks.mixins.crafting.curses;

import com.llamalad7.mixinextras.sugar.Local;
import minitweaks.MiniTweaksSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin {
    @Inject(method = "craft", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", shift = At.Shift.AFTER), cancellable = true)
    private void craftCursedFilter(CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 2) ItemStack itemStack4) {
        // skip checking for curses and adding enchants to crafted item and just return output item
        if(MiniTweaksSettings.removableCurses) {
            cir.setReturnValue(itemStack4);
        }
    }
}
