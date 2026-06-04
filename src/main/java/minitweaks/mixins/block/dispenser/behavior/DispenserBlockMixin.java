package minitweaks.mixins.block.dispenser.behavior;

import minitweaks.dispenser.MiniTweaksDispenserBehaviors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {
    @Inject(method = "dispenseFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/DispenserBlock;getDispenseMethod(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/core/dispenser/DispenseItemBehavior;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getBlockInFront(ServerLevel level, BlockState state, BlockPos pos, CallbackInfo ci, DispenserBlockEntity dispenserBlockEntity, BlockSource blockSource, int slot, ItemStack itemStack) {
        DispenseItemBehavior customBehavior = MiniTweaksDispenserBehaviors.getCustomDispenserBehavior(level, pos, blockSource, itemStack);
        if(customBehavior != null) {
            dispenserBlockEntity.setItem(slot, customBehavior.dispense(blockSource, itemStack));
            ci.cancel();
        }
    }
}
