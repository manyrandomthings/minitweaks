package minitweaks.mixins.mob.villager.bed_explode;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
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
        if(MiniTweaksSettings.villagersExplodeBeds && !world.getDimension().isBedWorking()) {
            // remove bed
            BlockState state = world.getBlockState(pos);
            world.removeBlock(pos, false);
            BlockPos blockPos = pos.offset((state.get(BedBlock.FACING)).getOpposite());
            if(world.getBlockState(blockPos).getBlock() instanceof BedBlock) {
                world.removeBlock(blockPos, false);
            }

            // create explosion
            Explosion.DestructionType type = world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            world.createExplosion(null, DamageSource.badRespawnPoint(), null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true, type);

            // cancel sleeping
            ci.cancel();
        }
    }
}
