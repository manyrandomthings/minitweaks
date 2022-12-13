package minitweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import carpet.settings.SettingsManager;
import minitweaks.MiniTweaksSettings;
import net.minecraft.server.command.SeedCommand;
import net.minecraft.server.command.ServerCommandSource;

@Mixin(SeedCommand.class)
public class SeedCommand_PermissionLevelMixin {
    // .requres() lambda in register method
    @SuppressWarnings("target")
    @Redirect(method = "method_13618", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"))
    private static boolean permissionLevelCheck(ServerCommandSource serverCommandSource, int original) {
        return SettingsManager.canUseCommand(serverCommandSource, MiniTweaksSettings.commandSeed);
    }
}
