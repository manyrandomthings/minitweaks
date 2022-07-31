package minitweaks;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;

public class MiniTweaks implements ModInitializer {
    public static final String MOD_ID = "minitweaks";
    public static CarpetExtension EXTENSION = new MiniTweaksCarpetExtension();

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(EXTENSION);
    }
}
