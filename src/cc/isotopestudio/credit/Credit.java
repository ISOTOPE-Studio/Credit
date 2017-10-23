package cc.isotopestudio.credit;

import cc.isotopestudio.credit.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Credit extends JavaPlugin {

    private static final String pluginName = "Credit";

    static PluginFile playerData;

    @Override
    public void onEnable() {
        playerData = new PluginFile(this, "player.yml");

        this.getCommand("credit").setExecutor(new CommandCredit());

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        getLogger().info(pluginName + " successfully loaded!");
        getLogger().info(pluginName + " is made by ISOTOPE Studio");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "successfully unloaded!");
    }

}
