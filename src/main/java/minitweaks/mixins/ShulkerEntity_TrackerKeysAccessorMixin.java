package minitweaks.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

@Mixin(ShulkerEntity.class)
public interface ShulkerEntity_TrackerKeysAccessorMixin {
    // ShulkerEntity.COLOR datatracker key
    @Accessor("COLOR")
    static TrackedData<Byte> getColorTrackerKey() {
        throw null;
    }

    // ShulkerEntity.ATTACHED_FACE datatracker key
    @Accessor("ATTACHED_FACE")
    static TrackedData<Direction> getAttachedFaceTrackerKey() {
        throw null;
    }

    // ShulkerEntity.ATTACHED_BLOCK datatracker key
    @Accessor("ATTACHED_BLOCK")
    static TrackedData<Optional<BlockPos>> getAttachedBlockTrackerKey() {
        throw null;
    }
}
