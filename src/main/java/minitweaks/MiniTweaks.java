package minitweaks;

import carpet.CarpetExtension;
import carpet.CarpetServer;

public class MiniTweaks implements CarpetExtension {
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
}
