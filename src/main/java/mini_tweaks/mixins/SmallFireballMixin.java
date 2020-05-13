package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(SmallFireballEntity.class)
public class SmallFireballMixin {
    @Redirect(method = "onBlockHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAir(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean preventFire(World world, BlockPos pos) {
        if(MiniTweaksSettings.disableBlazeFire) {
            // trick into thinking the block isn't air so it doesn't place fire
            return false;
        }
        return world.isAir(pos);
    }
}
