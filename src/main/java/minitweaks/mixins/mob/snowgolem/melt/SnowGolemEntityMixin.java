package minitweaks.mixins.mob.snowgolem.melt;

import minitweaks.MiniTweaksSettings;
import net.minecraft.client.realms.Request;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;isHot(Lnet/minecraft/util/math/BlockPos;)Z"), expect = 1, require = 0)
    private boolean isHotRedirect(Biome biome, BlockPos pos) {
        if(MiniTweaksSettings.noSnowGolemMelting) {
            return false;
        }

        return biome.isHot(pos);
    }
}
