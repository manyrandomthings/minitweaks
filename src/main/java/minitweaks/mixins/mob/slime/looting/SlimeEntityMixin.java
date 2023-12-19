package minitweaks.mixins.mob.slime.looting;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity {
    protected SlimeEntityMixin(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    @ModifyExpressionValue(method = "remove", at = @At(value = "CONSTANT", args = "intValue=3"))
    private int addLootingLevel(int original) {
        return original + (MiniTweaksSettings.slimeLooting && this.attackingPlayer != null ? EnchantmentHelper.getLooting(this.attackingPlayer) : 0);
    }
}
