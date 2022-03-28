package minitweaks.mixins.command.seed.permission;

import carpet.settings.SettingsManager;
import minitweaks.MiniTweaksSettings;
import net.minecraft.server.command.SeedCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SeedCommand.class)
public abstract class SeedCommandMixin {
    // .requres() lambda in register method
    @Redirect(method = "method_13618", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"))
    private static boolean permissionLevelCheck(ServerCommandSource serverCommandSource, int original) {
        return SettingsManager.canUseCommand(serverCommandSource, MiniTweaksSettings.commandSeed);
    }
}
