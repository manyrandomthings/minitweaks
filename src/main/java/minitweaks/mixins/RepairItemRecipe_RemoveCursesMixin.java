package minitweaks.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import minitweaks.MiniTweaksSettings;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;

@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipe_RemoveCursesMixin {
    @Inject(method = "craft", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", shift = At.Shift.AFTER), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void craftCursedFilter(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> cir, List<?> list, ItemStack itemStack3, ItemStack itemStack4, Item item, int j, int k, int l, int m, ItemStack itemStack5) {
        // skip checking for curses and adding enchants to crafted item and just return output item
        if(MiniTweaksSettings.removableCurses) {
            cir.setReturnValue(itemStack5);
        }
    }
}
