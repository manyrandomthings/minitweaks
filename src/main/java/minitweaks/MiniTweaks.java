package minitweaks;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;

public class MiniTweaks implements CarpetExtension, ModInitializer {
    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new MiniTweaks());
    }

    @Override
    public void onGameStarted() {
        // load minitweaks settings to carpet
        CarpetServer.settingsManager.parseSettingsClass(MiniTweaksSettings.class);
    }
}
