package fr.plhume.bungeeconnect.objects;

import org.bukkit.Material;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServersObject {

    private String serverName;
    private String bungeeName;
    private Material material;
    private int slot;
    private String status;
    private String description;
    private boolean display;

    public ServersObject(ResultSet server) throws SQLException {
        serverName = server.getString("name");
        bungeeName = server.getString("bungeeName");
        material = Material.valueOf(server.getString("material"));
        slot = server.getInt("slot");
        status = server.getString("status");
        description = server.getString("description");
        display = server.getBoolean("display");
    }

    public String getServerName() {
        return serverName;
    }

    public String getBungeeName() {
        return bungeeName;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getSlot() {
        return slot;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isDisplay() { return display; }
}
