package fr.gregwll.combatGuard.utils;

import fr.gregwll.combatGuard.CombatGuard;

import java.io.File;

public class Constents {

    public static String getPrefix() {
        return "§f[" + CombatGuard.getInstance().getConfigManager().getPrefix() + "§f] ";
    }

    public static File getSaveDir() {
        return new File(CombatGuard.getInstance().getDataFolder(), "players/");
    }
}