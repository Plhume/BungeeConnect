package fr.plhume.bungeeconnect;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BungeeConnect extends JavaPlugin {

    private String serverName;
    private String[] servers;
    private final Map<UUID, String> mapChangeName = new HashMap<>();
    private final Map<UUID, Location> teleportMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    public String getServerName() {
        return this.serverName;
    }

    public void setServerName() {
        this.serverName = serverName;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }


    public String[] getServers() {
        return this.servers;
    }
    public Map<UUID, Location> getTeleportMap() {
        return teleportMap;
    }

    public Map<UUID, String> getMapChangeName() {
        return this.mapChangeName;
    }
}
