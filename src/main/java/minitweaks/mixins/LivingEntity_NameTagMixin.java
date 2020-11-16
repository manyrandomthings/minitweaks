package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntity_NameTagMixin extends Entity {
    public LivingEntity_NameTagMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;drop(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void dropNameTag(CallbackInfo ci) {
        // if rule is enabled and mob has custom name
        if(MiniTweaksSettings.mobsDropNametag && this.hasCustomName()) {
            // create name tag
            ItemStack nameTag = new ItemStack(Items.NAME_TAG);
            // set name tag to mob's name
            nameTag.setCustomName(this.getCustomName());
            // drop item
            this.dropStack(nameTag);
        }
    }
}
