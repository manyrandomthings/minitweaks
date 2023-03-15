package minitweaks.mixins.mob.snowgolem.melt;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/entry/RegistryEntry;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"), expect = 1, require = 0)
    private boolean isHotRedirect(RegistryEntry<Biome> biome, TagKey<Biome> biomeTag) {
        if(MiniTweaksSettings.noSnowGolemMelting) {
            return false;
        }

        return biome.isIn(biomeTag);
    }
}
