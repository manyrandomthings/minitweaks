package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ItemEntity;

@Mixin(ItemEntity.class)
public interface ItemEntity_AgeAccessorMixin {
    // allows ItemEntity.age to be changed
    @Accessor("itemAge")
    void setItemAge(int itemAge);
}
