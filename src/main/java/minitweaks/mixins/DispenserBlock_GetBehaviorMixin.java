package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import minitweaks.MiniTweaksDispenserBehaviors;
import minitweaks.MiniTweaksSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlock_GetBehaviorMixin {
    private BlockPointer blockPointer;

    @Inject(method = "dispense", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/DispenserBlock;getBehaviorForItem(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/block/dispenser/DispenserBehavior;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getBlockInFront(ServerWorld serverWorld, BlockPos pos, CallbackInfo ci, BlockPointerImpl blockPointerImpl) {
        this.blockPointer = blockPointerImpl;
    }

    @Inject(method = "getBehaviorForItem", at = @At("HEAD"), cancellable = true)
    private void getCustomBehavior(ItemStack stack, CallbackInfoReturnable<DispenserBehavior> cir) {
        Item item = stack.getItem();

        // behavior for name tag with name
        if(MiniTweaksSettings.dispensersNameMobs && item == Items.NAME_TAG && stack.hasCustomName()) {
            cir.setReturnValue(MiniTweaksDispenserBehaviors.NAME_TAG);
        }
        // behavior for dye items
        else if(MiniTweaksSettings.dispensersDyeMobs && item instanceof DyeItem) {
            cir.setReturnValue(MiniTweaksDispenserBehaviors.DYE_ITEM);
        }
        // behavior for golden apples
        else if(MiniTweaksSettings.dispensersCureVillagers && item == Items.GOLDEN_APPLE) {
            cir.setReturnValue(MiniTweaksDispenserBehaviors.GOLDEN_APPLE);
        }
        // custom behaviors including block pointer
        else if(this.blockPointer != null) {
            // todo
        }
    }
}
