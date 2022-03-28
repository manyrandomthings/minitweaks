package minitweaks.mixins.mob.player.drops;

import minitweaks.MiniTweaksSettings;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PlayerInventory.class, priority = 999)
public abstract class PlayerInventoryMixin {
    @Redirect(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"), require = 0)
    private ItemEntity modifyAge(PlayerEntity player, ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
        // drops item
        ItemEntity droppedItem = player.dropItem(stack, throwRandomly, retainOwnership);

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
