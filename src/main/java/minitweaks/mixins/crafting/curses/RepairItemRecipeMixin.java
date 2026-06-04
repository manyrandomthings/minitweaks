package minitweaks.mixins.crafting.curses;

import com.llamalad7.mixinextras.sugar.Local;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin {
    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V", shift = At.Shift.AFTER), cancellable = true)
    private void craftCursedFilter(CallbackInfoReturnable<ItemStack> cir, @Local(name = "itemStack") ItemStack itemStack) {
        // skip checking for curses and adding enchants to crafted item and just return output item
        if(MiniTweaksSettings.removableCurses) {
            cir.setReturnValue(itemStack);
        }
    }
}
