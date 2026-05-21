package minitweaks.mixins.mob.shulker.dye;

import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(Shulker.class)
public interface ShulkerEntityInvoker {
    @Invoker("setVariant")
    void invokeSetColor(Optional<DyeColor> color);
}
