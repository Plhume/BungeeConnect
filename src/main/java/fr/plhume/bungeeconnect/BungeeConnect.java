package fr.plhume.bungeeconnect;

import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeConnect extends JavaPlugin {

    private String serverName;
    private String[] servers;
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
    }
}
