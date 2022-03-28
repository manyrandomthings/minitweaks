package minitweaks.mixins.mob.zombie.convert;

import net.minecraft.entity.mob.ZombieVillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.UUID;

@Mixin(ZombieVillagerEntity.class)
public interface ZombieVillagerEntityInvoker {
    // allows ZombieVillagerEntity.setConverting() to be used
    @Invoker("setConverting")
    void invokeSetConverting(UUID uuid, int delay);
}
