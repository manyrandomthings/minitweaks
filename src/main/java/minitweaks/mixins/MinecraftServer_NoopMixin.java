package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minitweaks.MiniTweaks;
import net.minecraft.server.Main;

@Mixin(Main.class)
public class MinecraftServer_NoopMixin {
    // this is here just to load the ExampleExtension class, otherwise noone would load it / need it
    // if you have already you own mixins that use your extension class in any shape or form
    // you don't need this one
    // You need this one to run a server properly
    @Inject(method = "main", at = @At("HEAD"))
    private static void loadMe(String[] args, CallbackInfo ci) {
        MiniTweaks.noop();
    }
}