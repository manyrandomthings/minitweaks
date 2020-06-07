package mini_tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mini_tweaks.MiniTweaks;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClient_NoopMixin {
    // this is here just to load the ExampleExtension class, otherwise noone would load it / need it
    // if you have already you own mixins that use your extension class in any shape or form
    // you don't need this one
    // This one is for the client
    @Inject(method = "<init>", at = @At("RETURN"))
    private void loadMe(CallbackInfo ci) {
        MiniTweaks.noop();
    }
}
