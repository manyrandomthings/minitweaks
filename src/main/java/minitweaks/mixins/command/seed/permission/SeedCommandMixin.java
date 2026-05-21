package minitweaks.mixins.command.seed.permission;

import carpet.utils.CommandHelper;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minitweaks.MiniTweaksSettings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.SeedCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(SeedCommand.class)
public abstract class SeedCommandMixin {
    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;requires(Ljava/util/function/Predicate;)Lcom/mojang/brigadier/builder/ArgumentBuilder;"))
    private static ArgumentBuilder<?, ?> permissionLevelCheck(LiteralArgumentBuilder<CommandSourceStack> instance, Predicate<?> predicate) {
        return instance.requires((player) -> CommandHelper.canUseCommand(player, MiniTweaksSettings.commandSeed));
    }
}
