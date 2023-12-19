package minitweaks.mixins.mob.sheep.dye;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.item.DyeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @ModifyExpressionValue(method = "useOnEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;isSheared()Z"))
    private boolean alwaysDyeSheep(boolean original) {
        return original && !MiniTweaksSettings.dyeableShearedSheep;
    }
}
