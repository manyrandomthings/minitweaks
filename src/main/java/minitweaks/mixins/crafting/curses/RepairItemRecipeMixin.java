package minitweaks.mixins.crafting.curses;

import minitweaks.MiniTweaksSettings;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin {
    @Inject(method = "craft", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", shift = At.Shift.AFTER), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void craftCursedFilter(CraftingInventory craftingInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir, List<ItemStack> list, ItemStack itemStack, ItemStack itemStack3, Item item, int j, int k, int l, int m, ItemStack itemStack4) {
        // skip checking for curses and adding enchants to crafted item and just return output item
        if(MiniTweaksSettings.removableCurses) {
            cir.setReturnValue(itemStack4);
        }
    }
}
