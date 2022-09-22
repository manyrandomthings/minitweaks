package minitweaks.mixins.mob.shulker.dye;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ShulkerEntity.class)
public interface ShulkerEntityInvoker {
    @Invoker("setColor")
    void invokeSetColor(DyeColor color);

    @Accessor("COLOR")
    static TrackedData<Byte> getColorTrackerKey() {
        throw null;
    }
}
