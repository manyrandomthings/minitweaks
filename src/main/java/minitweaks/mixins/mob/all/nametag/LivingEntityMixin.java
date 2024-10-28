package minitweaks.mixins.mob.all.nametag;

import minitweaks.MiniTweaksSettings;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "drop", at = @At("TAIL"))
    private void dropNameTag(ServerWorld world, DamageSource damageSource, CallbackInfo ci) {
        // if rule is enabled and mob has custom name
        if(MiniTweaksSettings.mobsDropNametag && this.hasCustomName()) {
            // create name tag
            ItemStack nameTag = new ItemStack(Items.NAME_TAG);
            // set name tag to mob's name
            nameTag.set(DataComponentTypes.CUSTOM_NAME, this.getCustomName());
            // drop item
            this.dropStack(world, nameTag);
        }
    }
}
