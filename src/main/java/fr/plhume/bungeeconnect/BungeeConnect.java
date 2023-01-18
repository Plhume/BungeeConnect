package fr.plhume.bungeeconnect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;
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

    public void sendServer(Player player, String serverName) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(serverName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendMessage(ChatColor.DARK_GREEN + "[BungeeConnect] " + ChatColor.GOLD + "you will be connect to server " + ChatColor.WHITE + serverName);
        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
    }

    public void tpPlayer(Player player, Location location) {
        if (player != null && teleportMap.containsKey(player.getUniqueId())) {
            player.teleport(location);
            teleportMap.remove(player.getUniqueId());
        }
    }

    public void tpPlayer(Player player, Location location, String serverName, String worldName) {
        if (!serverName.equals(this.getServerName())) {
            this.sendServer(player, serverName);
        } else {
            location.setWorld(Bukkit.getWorld(worldName));
            player.teleport(location);
        }
    }

    public Map<UUID, Location> getTeleportMap() {
        return teleportMap;
    }

    public Map<UUID, String> getMapChangeName() {
        return this.mapChangeName;
    }
}
