package minitweaks.mixins.mob.sheep.dye;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.item.DyeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @ModifyExpressionValue(method = "interactLivingEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/sheep/Sheep;isSheared()Z"))
    private boolean alwaysDyeSheep(boolean original) {
        return original && !MiniTweaksSettings.dyeableShearedSheep;
    }
}
