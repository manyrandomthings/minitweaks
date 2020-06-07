package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import mini_tweaks.MiniTweaksSettings;
import net.minecraft.world.gen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawner_SpawnTimeMixin {
    @ModifyConstant(method = "spawn", constant = @Constant(intValue = 72000))
    private int phantomSpawnTime(int original) {
        return MiniTweaksSettings.phantomSpawningTime;
    }
}
