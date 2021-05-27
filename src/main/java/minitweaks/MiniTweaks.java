package minitweaks;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;
import net.fabricmc.api.ModInitializer;

public class MiniTweaks implements CarpetExtension, ModInitializer {
    public static SettingsManager settingsManager;

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new MiniTweaks());
    }

    @Override
    public void onGameStarted() {
        // create custom settingsManager
        settingsManager = new SettingsManager(null, MiniTweaksRuleCategory.MODNAME, "MiniTweaks");
        settingsManager.parseSettingsClass(MiniTweaksSettings.class);

        // load minitweaks settings to carpet
        CarpetServer.settingsManager.parseSettingsClass(MiniTweaksSettings.class);

        // workaround for rule being overwritten: https://github.com/gnembon/fabric-carpet/issues/802
        CarpetServer.settingsManager.addRuleObserver((source, rule, value) -> {
            ParsedRule<?> minitweaksRule = settingsManager.getRule(rule.name);
            ParsedRule<?> carpetRule = CarpetServer.settingsManager.getRule(rule.name);

            // check if the rule being changed exists in minitweaks, but isn't the same rule as the one in carpet's settingsManager
            // if so, update the rule (if types are the same)
            if(minitweaksRule != null && carpetRule != null && minitweaksRule != carpetRule && minitweaksRule.type == carpetRule.type) {
                minitweaksRule.set(source, value);
            }
        });
    }
}
