package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.entity.EquipmentSlot;

@Mixin(InfinityEnchantment.class)
public class InfinityEnchantment_InfinityMendingStackingMixin extends Enchantment {

    protected InfinityEnchantment_InfinityMendingStackingMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    public void modifyCanAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if(MiniTweaksSettings.infinityMendingStacking) {
            cir.setReturnValue(super.canAccept(other));
        }
    }
}
