package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.DyeColor;

@Mixin(ShulkerEntity.class)
public interface ShulkerEntity_GetColorInvokerMixin {
    @Invoker("setColor")
    void invokeSetColor(DyeColor color);
}
