package fr.gregwll.combatGuard.listeners;

import fr.gregwll.combatGuard.utils.CombatManager;
import fr.gregwll.combatGuard.utils.Constents;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CombatListener implements Listener {

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player attacker)) return;

        CombatManager.tag(victim);
        CombatManager.tag(attacker);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            CombatManager.saveKill(killer, victim);
            CombatManager.updateTabName(killer);
        }

        CombatManager.untag(victim);
        CombatManager.updateTabName(victim);
    }
}