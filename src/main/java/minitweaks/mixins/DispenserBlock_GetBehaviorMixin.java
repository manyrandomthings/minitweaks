package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import minitweaks.MiniTweaksDispenserBehaviors;
import minitweaks.MiniTweaksSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
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
            BlockPos blockPos = this.blockPointer.getBlockPos().offset(this.blockPointer.getBlockState().get(DispenserBlock.FACING));
            Block blockInFront = this.blockPointer.getWorld().getBlockState(blockPos).getBlock();

            // cauldron behaviors
            if(MiniTweaksSettings.dispensersUseCauldrons && blockInFront == Blocks.CAULDRON) {
                if(item == Items.WATER_BUCKET) {
                    cir.setReturnValue(MiniTweaksDispenserBehaviors.CAULDRON_WATER_BUCKET);
                }
                else if(item == Items.BUCKET) {
                    cir.setReturnValue(MiniTweaksDispenserBehaviors.CAULDRON_EMPTY_BUCKET);
                }
                else if(item == Items.POTION && PotionUtil.getPotion(stack) == Potions.WATER) {
                    cir.setReturnValue(MiniTweaksDispenserBehaviors.CAULDRON_WATER_BOTTLE);
                }
                else if(item == Items.GLASS_BOTTLE) {
                    cir.setReturnValue(MiniTweaksDispenserBehaviors.CAULDRON_GLASS_BOTTLE);
                }
                else if(item instanceof DyeableItem) {
                    cir.setReturnValue(MiniTweaksDispenserBehaviors.CAULDRON_UNDYE_ITEM);
                }
                else if(item instanceof BannerItem) {
                    cir.setReturnValue(MiniTweaksDispenserBehaviors.CAULDRON_REMOVE_BANNER_LAYER);
                }
                else if(item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof ShulkerBoxBlock) {
                    cir.setReturnValue(MiniTweaksDispenserBehaviors.CAULDRON_UNDYE_SHULKER_BOX);
                }
            }
        }
    }
}
