package minitweaks.mixins.mob.shulker.dye;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(DyeItem.class)
public abstract class DyeItemMixin {
    @Shadow
    abstract DyeColor getColor();

    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void dyeShulkers(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(MiniTweaksSettings.dyeableShulkers && entity instanceof ShulkerEntity shulkerEntity) {
            DyeColor dyeItemColor = this.getColor();
            DyeColor currentShulkerColor = shulkerEntity.getColor();

            // checks if shulker is alive and current color is different than the dye's color
            if(shulkerEntity.isAlive() && currentShulkerColor != dyeItemColor) {
                shulkerEntity.getWorld().playSoundFromEntity(user, shulkerEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                if(!user.getWorld().isClient) {
                    shulkerEntity.setVariant(Optional.of(dyeItemColor));
                    stack.decrement(1);
                }

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
