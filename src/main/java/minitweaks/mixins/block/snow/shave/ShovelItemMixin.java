package minitweaks.mixins.block.snow.shave;

import minitweaks.MiniTweaksSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShovelItem.class)
public abstract class ShovelItemMixin {
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true)
    private void shaveSnowLayer(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if(MiniTweaksSettings.shaveSnowLayers && !level.isClientSide() && blockState.is(Blocks.SNOW)) {
            Player player = context.getPlayer();
            int layers = blockState.getValue(SnowLayerBlock.LAYERS);
            ItemStack tool = context.getItemInHand();
            boolean hasSilkTouch = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.SILK_TOUCH.identifier()).get(), tool) > 0;
            // set to air if only one snow layer remains, otherwise remove one layer
            BlockState shavedBlockState = layers > 1 ? blockState.setValue(SnowLayerBlock.LAYERS, layers - 1) : Blocks.AIR.defaultBlockState();

            level.setBlock(blockPos, shavedBlockState, Block.UPDATE_ALL_IMMEDIATE);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, shavedBlockState));

            // drop snow layer if silk touch is used, otherwise drop snowball
            Block.popResource(level, blockPos, new ItemStack(hasSilkTouch ? Items.SNOW : Items.SNOWBALL));
            level.playSound(null, blockPos, SoundEvents.SNOW_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

            // damage tool
            if(player != null) {
                tool.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
            }

            // return success (swing arm)
            cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
        }
    }
}
