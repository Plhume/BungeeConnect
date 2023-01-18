package fr.plhume.bungeeconnect;

import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeConnect extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
