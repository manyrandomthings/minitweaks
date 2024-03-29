package minitweaks.mixins.block.snow.shave;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShovelItem.class)
public abstract class ShovelItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void shaveSnowLayer(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if(MiniTweaksSettings.shaveSnowLayers && !world.isClient && blockState.isOf(Blocks.SNOW)) {
            int layers = blockState.get(SnowBlock.LAYERS);
            ItemStack tool = context.getStack();
            boolean hasSilkTouch = EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0;
            // set to air if only one snow layer remains, otherwise remove one layer
            BlockState shavedBlockState = layers > 1 ? blockState.with(SnowBlock.LAYERS, layers - 1) : Blocks.AIR.getDefaultState();

            world.setBlockState(blockPos, shavedBlockState, Block.NOTIFY_ALL_AND_REDRAW);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, shavedBlockState));

            // drop snow layer if silk touch is used, otherwise drop snowball
            Block.dropStack(world, blockPos, new ItemStack(hasSilkTouch ? Items.SNOW : Items.SNOWBALL));
            world.playSound(null, blockPos, SoundEvents.BLOCK_SNOW_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

            // damage tool
            if(playerEntity != null) {
                tool.damage(1, playerEntity, (p) -> {
                    p.sendToolBreakStatus(context.getHand());
                });
            }

            // return success (swing arm)
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
