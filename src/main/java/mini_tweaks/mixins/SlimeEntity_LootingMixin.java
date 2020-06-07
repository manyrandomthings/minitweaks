package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;

@Mixin(SlimeEntity.class)
public class SlimeEntity_LootingMixin extends MobEntity {
    protected SlimeEntity_LootingMixin(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    @ModifyArg(method = "remove", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int addLootingLevel(int original) {
        if(this.attackingPlayer == null || !MiniTweaksSettings.slimeLooting) {
            return original;
        }
        return original + EnchantmentHelper.getLooting(this.attackingPlayer);
    }
}
