package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStack_RepairCostMixin {
    @Inject(method = "getRepairCost", at = @At("HEAD"), cancellable = true)
    private void getRepairCostFix(CallbackInfoReturnable<Integer> cir) {
        if(MiniTweaksSettings.noRepairCost) {
            // if enabled, always return repair cost of 0
            cir.setReturnValue(0);
        }
    }

    @Inject(method = "setRepairCost", at = @At("HEAD"), cancellable = true)
    private void setRepairCostFix(int repairCost, CallbackInfo ci) {
        if(MiniTweaksSettings.noRepairCost) {
            // if enabled, don't set repair cost
            ci.cancel();
        }
    }
}
