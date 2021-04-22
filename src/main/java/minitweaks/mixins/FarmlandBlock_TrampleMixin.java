package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlock_TrampleMixin extends Block {
    protected FarmlandBlock_TrampleMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void featherFallingCheck(World world, BlockPos pos, Entity entity, float distance, CallbackInfo ci) {
        if(MiniTweaksSettings.noFeatherFallingTrample && entity instanceof LivingEntity) {
            // check for feather falling level of 1 or more
            if(EnchantmentHelper.getEquipmentLevel(Enchantments.FEATHER_FALLING, (LivingEntity) entity) > 0) {
                // required for fall damage
                super.onLandedUpon(world, pos, entity, distance);
                ci.cancel();
            }
        }
    }
}
