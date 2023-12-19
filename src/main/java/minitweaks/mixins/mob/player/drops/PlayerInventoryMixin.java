package minitweaks.mixins.mob.player.drops;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @ModifyExpressionValue(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"))
    private ItemEntity modifyAge(ItemEntity droppedItem) {
        // check for null since player.dropItem() is nullable
        if(droppedItem != null) {
            // modifies age
            ((ItemEntityAccessor) droppedItem).setItemAge(minutesToTicks(MiniTweaksSettings.deathItemsDespawnMinutes));
        }
        return droppedItem;
    }

    private static int minutesToTicks(int despawnMinutes) {
        // if minutes is -1, return -32768 (infinite age, see ItemEntity.tick())
        if (despawnMinutes == -1) {
            return -32768;
        }
        // converts minutes to ticks and subtracts from default despawn time
        return 6000 - (despawnMinutes * 60 * 20);
    }
}
