package minitweaks.mixins.mob.creeper.head_drops;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import minitweaks.MiniTweaksSettings;
import net.minecraft.world.entity.monster.Creeper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Creeper.class)
public abstract class CreeperMixin {
    @ModifyExpressionValue(method = "killedEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/monster/Creeper;droppedSkulls:Z", opcode = Opcodes.GETFIELD))
    private boolean dropHeads(boolean original) {
        return original && !MiniTweaksSettings.allChargedCreeperHeadsDrop;
    }
}
