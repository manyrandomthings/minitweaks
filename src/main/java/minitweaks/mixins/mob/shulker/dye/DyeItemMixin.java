package minitweaks.mixins.mob.shulker.dye;

import minitweaks.MiniTweaksSettings;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(DyeItem.class)
public abstract class DyeItemMixin {
    @Shadow
    abstract DyeColor getDyeColor();

    @Inject(method = "interactLivingEntity", at = @At("HEAD"), cancellable = true)
    private void dyeShulkers(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if(MiniTweaksSettings.dyeableShulkers && entity instanceof Shulker shulkerEntity) {
            DyeColor dyeItemColor = this.getDyeColor();
            DyeColor currentShulkerColor = shulkerEntity.getColor();

            // checks if shulker is alive and current color is different than the dye's color
            if(shulkerEntity.isAlive() && currentShulkerColor != dyeItemColor) {
                shulkerEntity.level().playSound(user, shulkerEntity, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                if(!user.level().isClientSide()) {
                    ((ShulkerEntityInvoker) shulkerEntity).invokeSetColor(Optional.of(dyeItemColor));
                    stack.shrink(1);
                }

                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
