package fr.plhume.bungeeconnect.managers;

import fr.plhume.bungeeconnect.BungeeConnect;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

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
        worldTable = dbInfos.getString("database.tables.worlds");
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
