package minitweaks.mixins.mob.phantom.spawning;

import minitweaks.MiniTweaksSettings;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 72000), require = 0, expect = 1)
    private int phantomSpawnTime(int original) {
        return MiniTweaksSettings.phantomSpawningTime;
    }
}
