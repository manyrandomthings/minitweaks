package minitweaks.mixins.mob.all.nametag;

import minitweaks.MiniTweaksSettings;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "dropAllDeathLoot", at = @At("TAIL"))
    private void dropNameTag(ServerLevel world, DamageSource damageSource, CallbackInfo ci) {
        // if rule is enabled and mob has custom name
        if(MiniTweaksSettings.mobsDropNametag && this.hasCustomName()) {
            // create name tag
            ItemStack nameTag = new ItemStack(Items.NAME_TAG);
            // set name tag to mob's name
            nameTag.set(DataComponents.CUSTOM_NAME, this.getCustomName());
            // drop item
            this.spawnAtLocation(world, nameTag);
        }
    }
}
