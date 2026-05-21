package minitweaks.mixins.mob.shulker.dye;

import minitweaks.MiniTweaksSettings;
import minitweaks.mixins.mob.all.interact.MobEntityMixin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Shulker.class)
public abstract class ShulkerEntityMixin extends MobEntityMixin {
    protected ShulkerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Shadow
    abstract DyeColor getColor();


    @Override
    protected void interactMobInject(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        // check if rule is enabled, server side, and shulker has a color
        if(MiniTweaksSettings.dyeableShulkers && !this.level().isClientSide() && this.getColor() != null) {
            ItemStack stack = player.getItemInHand(hand);

            // check if item used is a water bottle
            if(stack.is(Items.POTION) && stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.WATER)) {
                // set color to none
                ((ShulkerEntityInvoker) this).invokeSetColor(Optional.empty());

                // play sound, give empty bottle
                this.level().playSound(null, this.blockPosition(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));

                // swing hand
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
