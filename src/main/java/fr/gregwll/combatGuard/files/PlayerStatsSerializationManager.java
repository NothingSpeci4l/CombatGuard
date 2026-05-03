package fr.gregwll.combatGuard.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gregwll.combatGuard.obj.PlayerStats;

public class PlayerStatsSerializationManager {

    private final Gson gson;

    public PlayerStatsSerializationManager() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(PlayerStats stats) {
        return this.gson.toJson(stats);
    }

    public PlayerStats deserialize(String json) {
        return this.gson.fromJson(json, PlayerStats.class);
    }
}