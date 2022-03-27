package minitweaks.mixins.mob.player.drops;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ItemEntity;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {
    // allows ItemEntity.age to be changed
    @Accessor("itemAge")
    void setItemAge(int itemAge);
}
