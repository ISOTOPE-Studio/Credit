package cc.isotopestudio.credit.sql;
/*
 * Created by david on 10/23/2017.
 * Copyright ISOTOPE Studio
 */


import cc.isotopestudio.credit.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.*;

import static cc.isotopestudio.credit.Credit.plugin;

public abstract class SqlManager {
    public static Connection c;
    public static Statement statement;

    public static boolean connectMySQL() {

        SQLite db = new SQLite(plugin.getDataFolder().getAbsolutePath(), "data.db");
        try {
            c = db.openConnection();
        } catch (ClassNotFoundException e) {
            plugin.getLogger().info("database error Error1");
            return false;
        } catch (SQLException e) {
            plugin.getLogger().info("database error Error2");
            return false;
        }
        try {
            statement = c.createStatement();
        } catch (SQLException e1) {
            plugin.getLogger().info("database error Error3");
            return false;
        }

        try {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS creditblock(" +
                            " player CHAR(20) NOT NULL," +
                            " location CHAR(50) NOT NULL," +
                            " block CHAR(15) NOT NULL" +
                            " );");
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.getLogger().info("database error Error4");
            return false;
        }
        return true;
    }

    public static void addRecord(Player player, Location loc, String item) {
        String locString = Util.locationToString(loc);
        try {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT * FROM creditblock WHERE location=?;");
            ps.setString(1, locString);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = c.prepareStatement(
                        "DELETE FROM creditblock WHERE location=?;");
                ps.setString(1, locString);
                ps.executeUpdate();
            }
            ps = c.prepareStatement(
                    "INSERT INTO creditblock (player, location, block) VALUES (?,?,?);");
            ps.setString(1, player.getName());
            ps.setString(2, locString);
            ps.setString(3, item);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static OfflinePlayer getRecord(Location loc, String item) {
        String locString = Util.locationToString(loc);
        try {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT * FROM creditblock WHERE location=?");
            ps.setString(1, locString);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getString("block").equals(item)) {
                ps = c.prepareStatement(
                        "DELETE FROM creditblock WHERE location=?;");
                ps.setString(1, locString);
                ps.executeUpdate();
                return Bukkit.getOfflinePlayer(rs.getString("player"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
