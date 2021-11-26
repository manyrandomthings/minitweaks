package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import minitweaks.MiniTweaksSettings;
import minitweaks.MiniTweaksSettings.ItemPickupType;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

@Mixin({
    AbstractSkeletonEntity.class,
    ZombieEntity.class
})
public abstract class SkeletonZombie_SetPickupMixin extends HostileEntity {
    protected SkeletonZombie_SetPickupMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At("TAIL"))
    private void modifyLootPickup(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        // checks if rule is enabled (not default)
        if(MiniTweaksSettings.mobItemPickup != ItemPickupType.DEFAULT) {
            // sets the mob's item pickup ability (true if always, false if never)
            this.setCanPickUpLoot(MiniTweaksSettings.mobItemPickup == ItemPickupType.ALWAYS);
        }
    }
}
