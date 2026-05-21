package minitweaks.mixins.mob.villager.bed_explode;

import minitweaks.MiniTweaksSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin extends AbstractVillager {
    public VillagerEntityMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "startSleeping", at = @At("HEAD"), cancellable = true)
    private void explodeBed(BlockPos pos, CallbackInfo ci) {
        Level world = this.level();
        // if rule enabled and beds explode in dimension
        if(MiniTweaksSettings.villagersExplodeBeds && world.environmentAttributes().getValue(EnvironmentAttributes.BED_RULE, pos).explodes()) {
            // remove bed
            BlockState state = world.getBlockState(pos);
            world.removeBlock(pos, false);
            BlockPos blockPos = pos.relative((state.getValue(BedBlock.FACING)).getOpposite());
            if(world.getBlockState(blockPos).getBlock() instanceof BedBlock) {
                world.removeBlock(blockPos, false);
            }

            // create explosion
            Vec3 vec3d = pos.getCenter();
            world.explode(null, world.damageSources().badRespawnPointExplosion(vec3d), null, vec3d, 5.0F, true, Level.ExplosionInteraction.BLOCK);

            // cancel sleeping
            ci.cancel();
        }
    }
}
