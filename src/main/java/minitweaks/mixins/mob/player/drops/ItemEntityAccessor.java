package minitweaks.mixins.mob.player.drops;

import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {
    // allows ItemEntity.age to be changed
    @Accessor("itemAge")
    void setItemAge(int itemAge);
}
