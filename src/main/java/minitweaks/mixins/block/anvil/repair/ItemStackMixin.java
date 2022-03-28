package minitweaks.mixins.block.anvil.repair;

import minitweaks.MiniTweaksSettings;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getRepairCost", at = @At("HEAD"), cancellable = true)
    private void getRepairCostFix(CallbackInfoReturnable<Integer> cir) {
        if(MiniTweaksSettings.noRepairCost) {
            // if enabled, always return repair cost of 0
            cir.setReturnValue(0);
        }
    }
}
