package minitweaks.mixins.mob.slime.looting;

import minitweaks.MiniTweaksSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity {
    protected SlimeEntityMixin(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    @ModifyArg(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
    private int addLootingLevel(int original) {
        if(this.attackingPlayer == null || !MiniTweaksSettings.slimeLooting) {
            return original;
        }
        return original + EnchantmentHelper.getLooting(this.attackingPlayer);
    }
}
