package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import minitweaks.MiniTweaksSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.state.property.Property;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntity_WaterMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;contains(Lnet/minecraft/state/property/Property;)Z"))
    private boolean checkWaterloggedState(BlockState blockState, Property<Boolean> property) {
        return !MiniTweaksSettings.moveableWaterloggedBlocks && blockState.contains(property);
    }
}
