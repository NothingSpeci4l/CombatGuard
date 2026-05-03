package fr.gregwll.combatGuard.cmd;

import fr.gregwll.combatGuard.CombatGuard;
import fr.gregwll.combatGuard.files.FileUtils;
import fr.gregwll.combatGuard.obj.PlayerStats;
import fr.gregwll.combatGuard.utils.Constents;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CStats implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Constents.getPrefix() + "§fUsage: /stats <player>");
            return true;
        }

        String targetName = args[0];
        File fStats = new File(Constents.getSaveDir(), targetName + ".json");

        if (!fStats.exists()) {
            sender.sendMessage(Constents.getPrefix() + "§fNo stats found for §l" + targetName + "§r§f.");
            return true;
        }

        PlayerStats stats = CombatGuard.getInstance().getStatsSerializationManager()
                .deserialize(FileUtils.loadContent(fStats));

        sender.sendMessage("");
        sender.sendMessage(Constents.getPrefix() + "§fStats for §1§l" + targetName);
        sender.sendMessage("  §9§lKills: §7" + stats.getKills());
        sender.sendMessage("  §9§lDeaths: §7" + stats.getDeaths());
        sender.sendMessage("  §9§lK/D: §7" + (stats.getDeaths() == 0 ? stats.getKills() : String.format("%.2f", (double) stats.getKills() / stats.getDeaths())));
        sender.sendMessage("  §9§lCombat logouts: §7" + stats.getCombatLogouts());
        sender.sendMessage("");

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(p.getName());
                }
            }
        }
        return suggestions;
    }
}