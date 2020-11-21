package minitweaks.mixins;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.mob.ZombieVillagerEntity;

@Mixin(ZombieVillagerEntity.class)
public interface ZombieVillagerEntity_setConvertingInvokerMixin {
    // allows ZombieVillagerEntity.setConverting() to be used
    @Invoker("setConverting")
    public void invokeSetConverting(UUID uuid, int delay);
}