package minitweaks.mixins.mob.slime.looting;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slime.class)
public abstract class SlimeEntityMixin extends Mob {
    protected SlimeEntityMixin(EntityType<? extends Mob> type, Level world) {
        super(type, world);
    }

    @ModifyExpressionValue(method = "remove", at = @At(value = "CONSTANT", args = "intValue=3"))
    private int addLootingLevel(int original) {
        Player playerEntity = this.getLastHurtByPlayer();
        if(MiniTweaksSettings.slimeLooting && playerEntity != null) {
            ItemStack weapon = playerEntity.getWeaponItem();
            int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.LOOTING.identifier()).get(), weapon);
            return original + lootingLevel;
        }
        return original;
    }
}
