package minitweaks.mixins;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.mob.ZombieVillagerEntity;

@Mixin(ZombieVillagerEntity.class)
public interface ZombieVillagerEntity_SetConvertingInvokerMixin {
    // allows ZombieVillagerEntity.setConverting() to be used
    @Invoker("setConverting")
    void invokeSetConverting(UUID uuid, int delay);
}
