package minitweaks.mixins.mob.pickup;

import minitweaks.MiniTweaksSettings;
import minitweaks.MiniTweaksSettings.ItemPickupType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
    AbstractSkeleton.class,
    Zombie.class
})
public abstract class SkeletonZombieMixin extends Monster {
    protected SkeletonZombieMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    private void modifyLootPickup(ServerLevelAccessor serverLevel, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        // checks if rule is enabled (not default)
        if(MiniTweaksSettings.mobItemPickup != ItemPickupType.DEFAULT) {
            // sets the mob's item pickup ability (true if always, false if never)
            this.setCanPickUpLoot(MiniTweaksSettings.mobItemPickup == ItemPickupType.ALWAYS);
        }
    }
}
