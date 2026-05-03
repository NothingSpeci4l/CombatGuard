package fr.gregwll.combatGuard.files;

import fr.gregwll.combatGuard.CombatGuard;

public class ConfigManager {

    private final CombatGuard plugin;

    public ConfigManager(CombatGuard plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public int getCombatDuration() {
        return plugin.getConfig().getInt("combat-duration", 15);
    }

    public String getPunishment() {
        return plugin.getConfig().getString("punishment", "kill");
    }

    public int getBanDuration() {
        return plugin.getConfig().getInt("ban-duration", 300);
    }

    public boolean isTabStatsEnabled() {
        return plugin.getConfig().getBoolean("tab-stats", false);
    }

    public String getPrefix() {
        return plugin.getConfig().getString("prefix", "§4§lCombatGuard §8» §f");
    }

    public void reload() {
        plugin.reloadConfig();
    }
}