package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;

@Mixin(DyeItem.class)
public abstract class DyeItem_DyeShulkersMixin {
    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void dyeShulkers(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(MiniTweaksSettings.dyeableShulkers && entity instanceof ShulkerEntity) {
            ShulkerEntity shulkerEntity = (ShulkerEntity) entity;

            DyeColor dyeItemColor = ((DyeItem) (Object) this).getColor();
            DyeColor currentShulkerColor = shulkerEntity.getColor();

            // checks if shulker is alive and current color is different than the dye's color
            if(shulkerEntity.isAlive() && currentShulkerColor != dyeItemColor) {
                if(!user.world.isClient) {
                    ((ShulkerEntity_GetColorInvokerMixin) shulkerEntity).invokeSetColor(dyeItemColor);
                    stack.decrement(1);
                }

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
