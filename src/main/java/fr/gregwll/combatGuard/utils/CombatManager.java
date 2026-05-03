package fr.gregwll.combatGuard.utils;

import fr.gregwll.combatGuard.CombatGuard;
import fr.gregwll.combatGuard.files.FileUtils;
import fr.gregwll.combatGuard.files.PlayerStatsSerializationManager;
import fr.gregwll.combatGuard.obj.PlayerStats;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.BanList;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class CombatManager {

    private static final Map<UUID, Integer> taggedPlayers = new HashMap<>();
    private static final Map<UUID, Integer> taskIds = new HashMap<>();

    public static boolean isTagged(Player player) {
        return taggedPlayers.containsKey(player.getUniqueId());
    }

    public static int getTimeLeft(Player player) {
        return taggedPlayers.getOrDefault(player.getUniqueId(), 0);
    }

    public static void tag(Player player) {
        int duration = CombatGuard.getInstance().getConfigManager().getCombatDuration();
        taggedPlayers.put(player.getUniqueId(), duration);

        Integer existingTask = taskIds.get(player.getUniqueId());
        if (existingTask != null) {
            Bukkit.getScheduler().cancelTask(existingTask);
        }

        int taskId = Bukkit.getScheduler().runTaskTimer(CombatGuard.getInstance(), () -> {
            int timeLeft = taggedPlayers.getOrDefault(player.getUniqueId(), 0);

            if (timeLeft <= 0 || !player.isOnline()) {
                untag(player);
                return;
            }

            taggedPlayers.put(player.getUniqueId(), timeLeft - 1);

            int totalBars = 20;
            int filledBars = (int) Math.round((double) timeLeft / duration * totalBars);
            int emptyBars = totalBars - filledBars;
            String bar = "§c" + "█".repeat(filledBars) + "§8" + "█".repeat(emptyBars);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c⚔ " + bar + " §f" + timeLeft + "s"));

        }, 0L, 20L).getTaskId();

        taskIds.put(player.getUniqueId(), taskId);
    }

    public static void untag(Player player) {
        taggedPlayers.remove(player.getUniqueId());
        Integer taskId = taskIds.remove(player.getUniqueId());
        if (taskId != null) Bukkit.getScheduler().cancelTask(taskId);
        if (player.isOnline()) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aYou are no longer in combat."));
    }

    public static void handleCombatLogout(Player player) {
        String punishment = CombatGuard.getInstance().getConfigManager().getPunishment();

        saveLogout(player);

        if (punishment.equalsIgnoreCase("kill") || punishment.equalsIgnoreCase("both")) {
            player.setHealth(0);
            Bukkit.broadcastMessage(Constents.getPrefix() + "§f" + player.getName() + " §cdisconnected during combat !");
        }

        if (punishment.equalsIgnoreCase("ban") || punishment.equalsIgnoreCase("both")) {
            int banSeconds = CombatGuard.getInstance().getConfigManager().getBanDuration();
            Date expiry = new Date(System.currentTimeMillis() + (banSeconds * 1000L));
            Bukkit.getBanList(BanList.Type.NAME).addBan(
                    player.getName(),
                    "§cCombat logout — banned for " + banSeconds + " seconds.",
                    expiry,
                    "CombatGuard"
            );
        }

        untag(player);
    }

    private static void saveLogout(Player player) {
        File fStats = new File(Constents.getSaveDir(), player.getName() + ".json");
        PlayerStatsSerializationManager psm = CombatGuard.getInstance().getStatsSerializationManager();

        PlayerStats stats;
        if (fStats.exists()) {
            stats = psm.deserialize(FileUtils.loadContent(fStats));
        } else {
            stats = new PlayerStats(player.getName());
        }

        stats.addCombatLogout();
        FileUtils.save(fStats, psm.serialize(stats));
    }

    public static void saveKill(Player killer, Player victim) {
        PlayerStatsSerializationManager psm = CombatGuard.getInstance().getStatsSerializationManager();

        File fKiller = new File(Constents.getSaveDir(), killer.getName() + ".json");
        PlayerStats killerStats = fKiller.exists()
                ? psm.deserialize(FileUtils.loadContent(fKiller))
                : new PlayerStats(killer.getName());
        killerStats.addKill();
        FileUtils.save(fKiller, psm.serialize(killerStats));

        File fVictim = new File(Constents.getSaveDir(), victim.getName() + ".json");
        PlayerStats victimStats = fVictim.exists()
                ? psm.deserialize(FileUtils.loadContent(fVictim))
                : new PlayerStats(victim.getName());
        victimStats.addDeath();
        FileUtils.save(fVictim, psm.serialize(victimStats));
    }

    public static void updateTabName(Player player) {
        if (!CombatGuard.getInstance().getConfigManager().isTabStatsEnabled()) return;
        File fStats = new File(Constents.getSaveDir(), player.getName() + ".json");
        if (!fStats.exists()) return;
        PlayerStats stats = CombatGuard.getInstance().getStatsSerializationManager()
                .deserialize(FileUtils.loadContent(fStats));
        player.setPlayerListName(player.getName() + " §8[§aK:" + stats.getKills() + " §cD:" + stats.getDeaths() + "§8]");
    }
}