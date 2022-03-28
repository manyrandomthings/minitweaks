package minitweaks.mixins.mob.shulker.dye;

import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ShulkerEntity.class)
public interface ShulkerEntityInvoker {
    @Invoker("setColor")
    void invokeSetColor(DyeColor color);
}
