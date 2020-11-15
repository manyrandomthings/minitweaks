package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.ShulkerEntity;

@Mixin(ShulkerEntity.class)
public interface ShulkerEntity_ColorTrackerKeyAccessorMixin {
    // ShulkerEntity.COLOR datatracker key
    @Accessor("COLOR")
    static TrackedData<Byte> getColorTrackerKey() {
        throw null;
    }
}
