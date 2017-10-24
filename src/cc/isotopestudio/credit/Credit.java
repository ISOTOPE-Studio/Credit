package cc.isotopestudio.credit;

import cc.isotopestudio.credit.sql.SqlManager;
import cc.isotopestudio.credit.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Credit extends JavaPlugin {

    private static final String pluginName = "Credit";

    public static Credit plugin;

    static PluginFile playerData;
    public static PluginFile config;

    @Override
    public void onEnable() {
        plugin = this;

        config = new PluginFile(this,"config.yml","config.yml");
        config.setEditable(false);

        if (!SqlManager.connectMySQL()) {
            getLogger().severe( pluginName+" cannot load!");
            getLogger().severe("Unable to connect to MySQL");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        playerData = new PluginFile(this, "player.yml");

        this.getCommand("credit").setExecutor(new CommandCredit());

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        ConfigUpdateTask.run();

        getLogger().info(pluginName + " successfully loaded!");
        getLogger().info(pluginName + " is made by ISOTOPE Studio");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "successfully unloaded!");
    }

}
