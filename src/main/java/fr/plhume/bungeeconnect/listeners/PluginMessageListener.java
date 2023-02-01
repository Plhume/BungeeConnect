package fr.plhume.bungeeconnect.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.plhume.bungeeconnect.BungeeConnect;
import fr.plhume.bungeeconnect.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

    private final BungeeConnect plugin;
    private final DatabaseManager db;

    public PluginMessageListener(BungeeConnect plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(channel.equals("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();

            if(subChannel.equals("GetServers")) {
                String[] servers = in.readUTF().split(", ");
                plugin.setServers(servers);
                db.checkServers(servers);
            }

            if (subChannel.equals("GetServer")) {
                String name = in.readUTF();
                plugin.getLogger().info("Server detected as : " + name);
                plugin.setServerName(name);
            }

            if (subChannel.equals("Teleport")) {

                boolean complete = false;

                double locX = 0;
                double locY = 0;
                double locZ = 0;
                UUID uuid = null;
                String worldName = null;

                short len = in.readShort();
                byte[] msgbytes = new byte[len];

                in.readFully(msgbytes);

                DataInputStream msgdata = new DataInputStream(new ByteArrayInputStream(msgbytes));
                try {
                    uuid = UUID.fromString(msgdata.readUTF());
                    worldName = msgdata.readUTF();
                    locX = msgdata.readDouble();
                    locY = msgdata.readDouble();
                    locZ = msgdata.readDouble();

                    complete = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (complete) {
                    Location location = new Location(Bukkit.getServer().getWorld(worldName), locX, locY, locZ);
                    plugin.getTeleportMap().put(uuid, location);
                }

            }
        }


    }

}
