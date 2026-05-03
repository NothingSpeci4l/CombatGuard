package fr.gregwll.combatGuard.listeners;

import fr.gregwll.combatGuard.utils.CombatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (CombatManager.isTagged(player)) {
            CombatManager.handleCombatLogout(player);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CombatManager.updateTabName(event.getPlayer());
    }
}