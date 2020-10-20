package mini_tweaks;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;

public class MiniTweaks implements CarpetExtension, ModInitializer {
    public static void noop() {
    }

    static {
        CarpetServer.manageExtension(new MiniTweaks());
    }

    @Override
    public void onGameStarted() {
        // load minitweaks settings to carpet
        CarpetServer.settingsManager.parseSettingsClass(MiniTweaksSettings.class);
    }

    @Override
    public void onInitialize() {
        // load custom dispenser behaviors
        DispenserBehaviorRules.init();
    }
}
