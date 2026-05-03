package fr.gregwll.combatGuard.cmd;

import fr.gregwll.combatGuard.CombatGuard;
import fr.gregwll.combatGuard.utils.Constents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CCombatGuard implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp() && !sender.hasPermission("combatguard.admin")) {
            sender.sendMessage(Constents.getPrefix() + "§fNo permission.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            CombatGuard.getInstance().getConfigManager().reload();
            sender.sendMessage(Constents.getPrefix() + "§fConfig reloaded.");
        } else {
            sender.sendMessage(Constents.getPrefix() + "§fUsage: /combatguard reload");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (!sender.isOp() && !sender.hasPermission("combatguard.admin")) return suggestions;

        if (args.length == 1) {
            if ("reload".startsWith(args[0].toLowerCase())) {
                suggestions.add("reload");
            }
        }

        return suggestions;
    }
}