package minitweaks.mixins.block.farmland.trample;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import minitweaks.MiniTweaksSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FarmBlock.class)
public abstract class FarmlandBlockMixin extends Block {
    protected FarmlandBlockMixin(Properties settings) {
        super(settings);
    }

    @WrapWithCondition(method = "fallOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FarmBlock;turnToDirt(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"))
    private boolean featherFallingCheck(Entity entity, BlockState state, Level world, BlockPos pos) {
        return !(MiniTweaksSettings.noFeatherFallingTrample && entity instanceof LivingEntity livingEntity && EnchantmentHelper.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FEATHER_FALLING.identifier()).get(), livingEntity) > 0);
    }
}
