package dev.floffah.griefpreventionworldedit;


import org.bukkit.plugin.java.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.plugin.*;

import java.util.concurrent.*;

public class Main extends JavaPlugin
{
    public static String PluginName;
    public static String PluginVersion;
    public static final Logger logger;
    public static Main plugin;

    static {
        Main.PluginName = "GreifPrevention-Anti-Worldedit";
        Main.PluginVersion = "";
        logger = Logger.getLogger("Minecraft");
    }

    public void onDisable() {
        final PluginDescriptionFile pdf = this.getDescription();
        Main.logger.info(String.valueOf(pdf.getName()) + " is now disabled.");
    }

    public void onEnable() {
        final PluginDescriptionFile pdf = this.getDescription();
        Main.logger.info(String.valueOf(pdf.getName()) + " v" + pdf.getVersion() + " is now enabled.");
        Main.PluginVersion = pdf.getVersion();
        Bukkit.getServer().getPluginManager().registerEvents(new Listener(), (Plugin)this);
        Settings.setup((Plugin)(Main.plugin = this));

        Bukkit.getScheduler().runTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GriefPreventionApi.init();
            }
        });
    }
}
