package minitweaks.mixins.mob.player.xp;

import minitweaks.MiniTweaksSettings;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @ModifyConstant(method = "getBaseExperienceReward", constant = @Constant(intValue = 100))
    private int modifyDropCount(int original) {
        return MiniTweaksSettings.maxPlayerXpDrop;
    }
}
