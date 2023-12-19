package minitweaks.mixins.block.farmland.trample;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import minitweaks.MiniTweaksSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends Block {
    protected FarmlandBlockMixin(Settings settings) {
        super(settings);
    }

    @WrapWithCondition(method = "onLandedUpon", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FarmlandBlock;setToDirt(Lnet/minecraft/entity/Entity;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private boolean featherFallingCheck(Entity entity, BlockState state, World world, BlockPos pos) {
        return !(MiniTweaksSettings.noFeatherFallingTrample && entity instanceof LivingEntity livingEntity && EnchantmentHelper.getEquipmentLevel(Enchantments.FEATHER_FALLING, livingEntity) > 0);
    }
}
