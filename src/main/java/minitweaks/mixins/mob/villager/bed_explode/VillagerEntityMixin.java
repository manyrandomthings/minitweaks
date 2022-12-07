package minitweaks.mixins.mob.villager.bed_explode;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "sleep", at = @At("HEAD"), cancellable = true)
    private void explodeBed(BlockPos pos, CallbackInfo ci) {
        World world = this.getWorld();
        // if rule enabled and beds explode in dimension
        if(MiniTweaksSettings.villagersExplodeBeds && !world.getDimension().bedWorks()) {
            // remove bed
            BlockState state = world.getBlockState(pos);
            world.removeBlock(pos, false);
            BlockPos blockPos = pos.offset((state.get(BedBlock.FACING)).getOpposite());
            if(world.getBlockState(blockPos).getBlock() instanceof BedBlock) {
                world.removeBlock(blockPos, false);
            }

            // create explosion
            Vec3d vec3d = pos.toCenterPos();
            world.createExplosion(null, DamageSource.badRespawnPoint(vec3d), null, vec3d, 5.0F, true, World.ExplosionSourceType.MOB);

            // cancel sleeping
            ci.cancel();
        }
    }
}
