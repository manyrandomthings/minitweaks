package minitweaks.mixins.enchant.bow;

import minitweaks.MiniTweaksSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InfinityEnchantment.class)
public abstract class InfinityEnchantmentMixin extends Enchantment {
    protected InfinityEnchantmentMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    private void modifyCanAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if(MiniTweaksSettings.infinityMendingStacking) {
            cir.setReturnValue(super.canAccept(other));
        }
    }
}
