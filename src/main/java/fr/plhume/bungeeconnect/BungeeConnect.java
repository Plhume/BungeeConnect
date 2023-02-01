package fr.plhume.bungeeconnect;

import fr.plhume.bungeeconnect.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BungeeConnect extends JavaPlugin {

    private String serverName;
    private String[] servers;
    private DatabaseManager db;
    private final Map<UUID, String> mapChangeName = new HashMap<>();
    private final Map<UUID, Location> teleportMap = new HashMap<>();

    public BungeeConnect() throws IOException, SQLException {
        db = new DatabaseManager(this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    public DatabaseManager getDatabaseManager() {
        return this.db;
    }

    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String serverName) {
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

    public void openServer(String serverName) {
        this.getDatabaseManager().setStatus(serverName, "open");
    }

    public void closeServer(String serverName) {
        this.getDatabaseManager().setStatus(serverName, "close");
    }
}
