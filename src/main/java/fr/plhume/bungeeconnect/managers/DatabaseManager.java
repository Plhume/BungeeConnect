package fr.plhume.bungeeconnect.managers;

import fr.plhume.bungeeconnect.BungeeConnect;
import org.bukkit.configuration.Configuration;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Plhume
 */
public class DatabaseManager {

    private BungeeConnect plugin;
    private Statement statement;
    private String serverTable;
    private String worldTable;

    private String jdbcString;
    private String DBUsername;
    private String DBPassword;

    public DatabaseManager(BungeeConnect plugin) throws SQLException, IOException {
        this.plugin = plugin;
        Configuration dbConfig = plugin.getConfig();
    }

}
