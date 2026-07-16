package minitweaks.mixins.mob.slime.looting;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractCubeMob.class)
public abstract class AbstractCubeMobMixin extends Mob {
    protected AbstractCubeMobMixin(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @ModifyExpressionValue(method = "getSplitCount", at = @At(value = "CONSTANT", args = "intValue=3"))
    private int addLootingLevel(int original) {
        Player player = this.getLastHurtByPlayer();
        if(MiniTweaksSettings.slimeLooting && player != null) {
            ItemStack weapon = player.getWeaponItem();
            int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.LOOTING.identifier()).get(), weapon);
            return original + lootingLevel;
        }
        return original;
    }
}
