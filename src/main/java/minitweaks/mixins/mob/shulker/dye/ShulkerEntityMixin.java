package minitweaks.mixins.mob.shulker.dye;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.mob.all.interact.MobEntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntityMixin extends MobEntityMixin {
    protected ShulkerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Final
    @Shadow
    private static TrackedData<Byte> COLOR;
    @Shadow
    abstract DyeColor getColor();


    @Override
    protected void interactMobInject(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        // check if rule is enabled, server side, and shulker has a color
        if(MiniTweaksSettings.dyeableShulkers && !this.world.isClient && this.getColor() != null) {
            ItemStack stack = player.getStackInHand(hand);

            // check item item used is a water bottle
            if(stack.isOf(Items.POTION) && PotionUtil.getPotion(stack) == Potions.WATER) {
                // set color to none
                this.getDataTracker().set(COLOR, (byte) 16);

                // play sound, give empty bottle
                world.playSound(null, this.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));

                // swing hand
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
