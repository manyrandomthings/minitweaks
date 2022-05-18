package minitweaks.mixins.mob.sheep.dye;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Redirect(method = "useOnEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;isSheared()Z"))
    private boolean alwaysDyeSheep(SheepEntity sheepEntity) {
        if(MiniTweaksSettings.dyeableShearedSheep) {
            return false;
        }

        return sheepEntity.isSheared();
    }
}
