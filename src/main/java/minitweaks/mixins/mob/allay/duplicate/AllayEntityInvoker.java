package minitweaks.mixins.mob.allay.duplicate;

import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Allay.class)
public interface AllayEntityInvoker {
    @Invoker("canDuplicate")
    boolean invokeCanDuplicate();

    @Invoker("duplicateAllay")
    void invokeDuplicate();
}
