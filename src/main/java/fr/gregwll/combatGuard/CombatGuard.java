package fr.gregwll.combatGuard;

import fr.gregwll.combatGuard.cmd.CCombatGuard;
import fr.gregwll.combatGuard.cmd.CStats;
import fr.gregwll.combatGuard.files.ConfigManager;
import fr.gregwll.combatGuard.files.PlayerStatsSerializationManager;
import fr.gregwll.combatGuard.listeners.CombatListener;
import fr.gregwll.combatGuard.listeners.DisconnectListener;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

public class CombatGuard extends JavaPlugin {

    private static CombatGuard instance;
    private ConfigManager configManager;
    private PlayerStatsSerializationManager statsSerializationManager;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.statsSerializationManager = new PlayerStatsSerializationManager();

        getServer().getPluginManager().registerEvents(new CombatListener(), this);
        getServer().getPluginManager().registerEvents(new DisconnectListener(), this);

        CStats cStats = new CStats();
        getCommand("stats").setExecutor(cStats);
        getCommand("stats").setTabCompleter(cStats);

        CCombatGuard cCombatGuard = new CCombatGuard();
        getCommand("combatguard").setExecutor(cCombatGuard);
        getCommand("combatguard").setTabCompleter(cCombatGuard);

        getLogger().info("CombatGuard enabled.");

        int pluginId = 31067;
        Metrics metrics = new Metrics(this, pluginId);

        // Optional: Add custom charts
        metrics.addCustomChart(
                new SimplePie("chart_id", () -> "My value")
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("CombatGuard disabled.");
    }

    public static CombatGuard getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerStatsSerializationManager getStatsSerializationManager() {
        return statsSerializationManager;
    }
}