package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import minitweaks.MiniTweaksSettings;
import net.minecraft.world.gen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawner_SpawnTimeMixin {
    @ModifyConstant(method = "spawn", constant = @Constant(intValue = 72000))
    private int phantomSpawnTime(int original) {
        return MiniTweaksSettings.phantomSpawningTime;
    }
}
