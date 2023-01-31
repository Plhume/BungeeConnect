package fr.plhume.bungeeconnect.managers;

import fr.plhume.bungeeconnect.BungeeConnect;
import fr.plhume.bungeeconnect.objects.ServersObject;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Plhume
 */
public class DatabaseManager {

    private final BungeeConnect plugin;
    private String serverTable;

    private String jdbcString;
    private String DBUsername;
    private String DBPassword;

    public DatabaseManager(BungeeConnect plugin) throws SQLException {
        this.plugin = plugin;
        Configuration dbConfig = plugin.getConfig();
        connect(dbConfig);
    }

    private void connect(Configuration dbInfos) throws SQLException {
        String host = dbInfos.getString("database.host");
        String port = dbInfos.getString("database.port");
        String dbName = dbInfos.getString("database.database");
        String username = dbInfos.getString("database.user");
        String password = dbInfos.getString("database.password");

        jdbcString = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        DBUsername = username;
        DBPassword = password;

        serverTable = dbInfos.getString("database.tables.servers");
        createTable();
    }

    private void createTable() throws SQLException {
        Objects.requireNonNull(this.getSqlStatement()).execute("CREATE TABLE IF NOT EXISTS `" + this.serverTable + "` (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45) NULL," +
                "`bungeeName` VARCHAR(45) NULL," +
                "`material` VARCHAR(45) NULL," +
                "`slot` INT NULL," +
                "`status` VARCHAR(45) NULL," +
                "`description` TEXT NULL," +
                "`display` BIT NULL," +
                "PRIMARY KEY (`id`))");
    }

    public List<ServersObject> getServersList() {
        List<ServersObject> serversList = new ArrayList<>();
        ResultSet result;

        try {
            result = Objects.requireNonNull(this.getSqlStatement()).executeQuery("SELECT * FROM " + this.serverTable);
            while (result.next()) {
                ServersObject servers = new ServersObject(result);
                serversList.add(servers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serversList;
    }

    public ServersObject getServer(String serverName) {
        ServersObject servers = null;
        ResultSet result;

        try {
            result = Objects.requireNonNull(this.getSqlStatement()).executeQuery("SELECT * FROM " + this.serverTable + " WHERE `name` = " + serverName);
            result.next();

            servers = new ServersObject(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servers;
    }

    public void checkServers(String[] servers) {
        List<String> serverLoaded = new ArrayList<>();
        List<ServersObject> serversList = this.getServersList();

        for (String serverName : servers) {
            for (ServersObject serverDBName : serversList) {
                if (serverName.equals(serverDBName.getBungeeName())) {
                    serverLoaded.add((serverDBName.getBungeeName()));
                }
            }
        }

        for (String server : servers) {
            if (!serverLoaded.contains(server)) {
                try {
                    Objects.requireNonNull(this.getSqlStatement()).execute("INSERT INTO " + this.serverTable + "(`name`, `bungeeName`, `material`, `slot`, `status`, `description`, `display`)" +
                            "VALUES ('" + server + "', '" + server + "', 'BEDROCK', '-1', 'close', '', 0)");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setMaterial(String serverName, Material material) {
        try {
            Objects.requireNonNull(this.getSqlStatement()).execute("UPDATE " + this.serverTable + " SET `material` = '" + material.name() + "' WHERE `name` = '" + serverName + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setServerName(String serverName, String newName) {
        try {
            Objects.requireNonNull(this.getSqlStatement()).execute("UPDATE " + this.serverTable + " SET `name` = '" + newName + "' WHERE `name` = '" + serverName + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStatus(String serverName, String status) {
        try {
            Objects.requireNonNull(this.getSqlStatement()).execute("UPDATE " + this.serverTable + " SET `status` = '" + status + "' WHERE `name` = '" + serverName + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDisplay(String serverName, boolean display, int index) {
        plugin.getLogger().info(display + " " + serverName);
        try {
            Objects.requireNonNull(this.getSqlStatement()).execute("UPDATE " + this.serverTable + " SET `display` = " + display + ", `slot` = '" + index + "' WHERE `name` = '" + serverName + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Statement getSqlStatement() {
        try {
            Connection conn = DriverManager.getConnection(this.jdbcString, this.DBUsername, this.DBPassword);
            return conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
