package minitweaks;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.api.settings.CarpetRule;
import carpet.api.settings.InvalidRuleValueException;
import carpet.api.settings.SettingsManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MiniTweaksCarpetExtension implements CarpetExtension {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiniTweaks.MOD_ID);
    public static SettingsManager settingsManager;

    @Override
    public void onGameStarted() {
        ModContainer mod = FabricLoader.getInstance().getModContainer(MiniTweaks.MOD_ID).orElseThrow(NullPointerException::new);

        // create settings manager
        settingsManager = new SettingsManager(mod.getMetadata().getVersion().getFriendlyString(), MiniTweaks.MOD_ID, mod.getMetadata().getName());

        // load carpet rules into settings manager(s)
        settingsManager.parseSettingsClass(MiniTweaksSettings.class);
        CarpetServer.settingsManager.parseSettingsClass(MiniTweaksSettings.class);

        this.createRuleOverwriteWorkaround();
    }

    private void createRuleOverwriteWorkaround() {
        // workaround for rule being overwritten: https://github.com/gnembon/fabric-carpet/issues/802
        SettingsManager.registerGlobalRuleObserver((source, changedRule, userInput) -> {
            CarpetRule<?> minitweaksRule = settingsManager.getCarpetRule(changedRule.name());
            CarpetRule<?> carpetRule = CarpetServer.settingsManager.getCarpetRule(changedRule.name());

            // check if the rule being changed exists in minitweaks, but isn't the same rule as the one in carpet's settingsManager
            // if so, update the rule (if types are the same)
            if(minitweaksRule != null && carpetRule != null && minitweaksRule != carpetRule && minitweaksRule.type() == carpetRule.type()) {
                try {
                    minitweaksRule.set(source, userInput);
                } catch (InvalidRuleValueException e) {
                    LOGGER.error("Error setting rule", e);
                }
            }
        });
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        InputStream langFile = MiniTweaks.class.getClassLoader().getResourceAsStream("assets/minitweaks/lang/%s.json".formatted(lang));
        if (langFile == null) {
            return Collections.emptyMap();
        }
        String jsonData;
        try {
            jsonData = IOUtils.toString(langFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        Gson gson = new GsonBuilder().setLenient().create();

        Map<String, String> map = gson.fromJson(jsonData, new TypeToken<Map<String, String>>() {}.getType());
        Map<String, String> map2 = new HashMap<>();

        // create translation keys for both carpet and minitweaks settingsManagers
        map.forEach((key, value) -> {
            map2.put(key, value);
            if(key.startsWith("minitweaks.rule.")) {
                map2.put(key.replace("minitweaks.rule.", "carpet.rule."), value);
            }
        });

        return map2;
    }
}
