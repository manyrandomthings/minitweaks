package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import minitweaks.dispenser.MiniTweaksDispenserBehaviors;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlock_GetBehaviorMixin {
    @Inject(method = "dispense", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/DispenserBlock;getBehaviorForItem(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/block/dispenser/DispenserBehavior;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getBlockInFront(ServerWorld serverWorld, BlockPos pos, CallbackInfo ci, BlockPointerImpl blockPointerImpl, DispenserBlockEntity dispenserBlockEntity, int i, ItemStack itemStack) {
        DispenserBehavior customBehavior = MiniTweaksDispenserBehaviors.getCustomDispenserBehavior(serverWorld, pos, blockPointerImpl, dispenserBlockEntity, itemStack);
        if(customBehavior != null) {
            dispenserBlockEntity.setStack(i, customBehavior.dispense(blockPointerImpl, itemStack));
            ci.cancel();
        }
    }
}
