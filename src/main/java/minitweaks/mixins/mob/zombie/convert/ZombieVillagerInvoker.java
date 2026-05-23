package minitweaks.mixins.mob.zombie.convert;

import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.UUID;

@Mixin(ZombieVillager.class)
public interface ZombieVillagerInvoker {
    // allows ZombieVillagerEntity.startConverting() to be used
    @Invoker("startConverting")
    void invokeStartConverting(UUID uuid, int delay);
}
