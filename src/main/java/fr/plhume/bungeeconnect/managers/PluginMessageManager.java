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

}
