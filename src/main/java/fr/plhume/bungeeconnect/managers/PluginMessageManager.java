package fr.plhume.bungeeconnect.managers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.plhume.bungeeconnect.BungeeConnect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessageManager {

    private final BungeeConnect plugin;

    public PluginMessageManager(BungeeConnect plugin) {
        this.plugin = plugin;
    }

    public void getServers(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        if (player != null) {
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        }
    }

    public void getServerName(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        if (player != null) {
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        }
    }

    public void sendTeleportRequest(Player player, Location location, String server, String worldName) {

        if (player == null) {
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF(server);
        out.writeUTF("Teleport");

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try {
            msgout.writeUTF(player.getUniqueId().toString());
            msgout.writeUTF(worldName);
            msgout.writeDouble(location.getX());
            msgout.writeDouble(location.getY());
            msgout.writeDouble(location.getZ());
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
