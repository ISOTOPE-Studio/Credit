package cc.isotopestudio.credit;
/*
 * Created by david on 10/22/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

import static cc.isotopestudio.credit.Credit.playerData;

abstract class CreditDataManager {

    public static String getItemString(int a, byte b) {
        return b == 0 ? a + ":0" : a + ":" + b;
    }

    public static int getItemId(String item) {
        return Integer.parseInt(item.split(":")[0]);
    }

    public static byte getItembyte(String item) {
        return (byte) Integer.parseInt(item.split(":")[1]);
    }

    public static Map<String, Integer> getPlayerCredit(OfflinePlayer player) {
        Map<String, Integer> result = new HashMap<>();
        if (playerData.isConfigurationSection(player.getName())) {
            ConfigurationSection config = playerData.getConfigurationSection(player.getName());
            for (String type : config.getKeys(false)) {
                result.put(type, config.getInt(type));
            }
        }
        return result;
    }

    public static int getPlayerCredit(OfflinePlayer player, int id, byte b) {
        if (playerData.isInt(player.getName() + "." + getItemString(id, b))) {
            return playerData.getInt(player.getName() + "." + getItemString(id, b));
        }
        return -1;
    }

    public static void setPlayerCredit(OfflinePlayer player, int id, byte b, int credit) {
        playerData.set(player.getName() + "." + getItemString(id, b), credit);
        playerData.save();
    }

    public static void addPlayerCredit(OfflinePlayer player, int type, byte b, int i) {
        int oldCredit = getPlayerCredit(player, type, b);
        if (oldCredit < 0) oldCredit = 0;
        setPlayerCredit(player, type, b, oldCredit + i);
    }

    public static void minusPlayerCredit(OfflinePlayer player, int id, byte b) {
        int credit = getPlayerCredit(player, id, b);
        if (credit <= 0) return;
        playerData.set(player.getName() + "." + getItemString(id, b), credit - 1);
        playerData.save();
    }

}
