package fr.gregwll.combatGuard.obj;

public class PlayerStats {

    private String playerName;
    private int kills;
    private int deaths;
    private int combatLogouts;

    public PlayerStats(String playerName) {
        this.playerName = playerName;
        this.kills = 0;
        this.deaths = 0;
        this.combatLogouts = 0;
    }

    public String getPlayerName() { return playerName; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public int getCombatLogouts() { return combatLogouts; }

    public void addKill() { this.kills++; }
    public void addDeath() { this.deaths++; }
    public void addCombatLogout() { this.combatLogouts++; }
}