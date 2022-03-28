package minitweaks.mixins.mob.player.xp;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @ModifyConstant(method = "getXpToDrop", constant = @Constant(intValue = 100), expect = 2)
    private int modifyDropCount(int original) {
        return MiniTweaksSettings.maxPlayerXpDrop;
    }
}
