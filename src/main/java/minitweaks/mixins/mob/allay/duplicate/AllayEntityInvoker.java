package minitweaks.mixins.mob.allay.duplicate;

import net.minecraft.entity.passive.AllayEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AllayEntity.class)
public interface AllayEntityInvoker {
    @Invoker("canDuplicate")
    boolean invokeCanDuplicate();

    @Invoker("duplicate")
    void invokeDuplicate();
}
