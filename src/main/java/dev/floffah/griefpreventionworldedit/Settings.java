package dev.floffah.griefpreventionworldedit;

import java.io.*;
import org.bukkit.plugin.*;
import org.bukkit.configuration.file.*;

public class Settings
{
    static File cfile;

    public static void setup(final Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        Settings.cfile = new File(p.getDataFolder(), "config.yml");
        if (!Settings.cfile.exists()) {
            System.out.println("Created config");
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public static FileConfiguration getConfig() {
        return Main.plugin.getConfig();
    }

    public static void saveConfig() {
        Main.plugin.saveConfig();
    }

    public static void reloadConfig() {
        Main.plugin.reloadConfig();
    }
}
