package minitweaks.mixins.block.dispenser.behavior;

import minitweaks.dispenser.MiniTweaksDispenserBehaviors;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {
    @Inject(method = "dispense", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/DispenserBlock;getBehaviorForItem(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/block/dispenser/DispenserBehavior;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getBlockInFront(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci, DispenserBlockEntity dispenserBlockEntity, BlockPointer blockPointer, int i, ItemStack itemStack) {
        DispenserBehavior customBehavior = MiniTweaksDispenserBehaviors.getCustomDispenserBehavior(world, pos, blockPointer, dispenserBlockEntity, itemStack);
        if(customBehavior != null) {
            dispenserBlockEntity.setStack(i, customBehavior.dispense(blockPointer, itemStack));
            ci.cancel();
        }
    }
}
