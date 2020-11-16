package mini_tweaks;

import mini_tweaks.mixins.ShulkerEntity_ColorTrackerKeyAccessorMixin;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.DyeColor;

public class ShulkerEntityColorHelper {
    // remove this class when 1.17 comes out

    public static DyeColor getColor(ShulkerEntity shulkerEntity) {
        DataTracker dataTracker = shulkerEntity.getDataTracker();
        TrackedData<Byte> COLOR = ShulkerEntity_ColorTrackerKeyAccessorMixin.getColorTrackerKey();
        Byte colorId = dataTracker.get(COLOR);

        if(colorId >= 0 && colorId <= 15) {
            return DyeColor.byId(colorId);
        }
        return null;
    }

    public static void setColor(ShulkerEntity shulkerEntity, DyeColor dyeColor) {
        DataTracker dataTracker = shulkerEntity.getDataTracker();
        TrackedData<Byte> COLOR = ShulkerEntity_ColorTrackerKeyAccessorMixin.getColorTrackerKey();
        byte colorId = (byte) (dyeColor != null ? dyeColor.getId() : 16);

        dataTracker.set(COLOR, colorId);
    }
}