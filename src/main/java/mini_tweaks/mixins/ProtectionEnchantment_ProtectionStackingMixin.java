package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;

@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantment_ProtectionStackingMixin extends Enchantment {

  protected ProtectionEnchantment_ProtectionStackingMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
    super(weight, type, slotTypes);
  }

  @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
  public void modifyCanAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
    if(MiniTweaksSettings.protectionStacking) {
      cir.setReturnValue(super.canAccept(other));
    }
  }
}
