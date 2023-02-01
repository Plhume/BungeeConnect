package fr.plhume.bungeeconnect.listeners;

import fr.plhume.bungeeconnect.BungeeConnect;
import fr.plhume.bungeeconnect.managers.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;

public class ChatListener implements Listener {

    private BungeeConnect plugin;
    private DatabaseManager db;
    
    public ChatListener(BungeeConnect plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
    }

    @EventHandler
    public void onChatEntry(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Map<UUID, String> mapChangeName = plugin.getMapChangeName();

        if (mapChangeName.containsKey(player.getUniqueId())) {
            String msg = e.getMessage();
            String serverName = mapChangeName.get(player.getUniqueId());

            db.getServer(serverName);
            db.setServerName(serverName, msg);

            mapChangeName.remove(player.getUniqueId());
            e.setCancelled(true);
            player.sendMessage(ChatColor.GREEN + "Le nom du serveur " + ChatColor.WHITE + serverName + ChatColor.GREEN + " à bien été modifié pour " + ChatColor.WHITE + msg);
        }
    }
}
