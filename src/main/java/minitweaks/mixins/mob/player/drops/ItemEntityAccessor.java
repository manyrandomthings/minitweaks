package minitweaks.mixins.mob.player.drops;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {
    // allows ItemEntity.age to be changed
    @Accessor("age")
    void setItemAge(int itemAge);
}
