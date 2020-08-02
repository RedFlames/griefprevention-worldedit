package dev.floffah.griefpreventionworldedit;

import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        if (GriefPreventionApi.usePlugin) {
            Settings.reloadConfig();
            String world = event.getPlayer().getWorld().toString().toLowerCase();
            String cmd = event.getMessage().split(" ")[0].toLowerCase();
            cmd = cmd.replace("//", "");
            cmd = cmd.replace("/", "");
            world = world.replace("craftworld{name=", "");
            world = world.replace("}", "");
            boolean cancel = false;
            if (!event.getPlayer().hasPermission("koshi.nowe")) {
                Boolean isCmd = false;
                for (String cm : Settings.getConfig().getStringList("Settings.WorldEditCmds")) {
                    if(cmd.startsWith(cm)) {
                        isCmd = true;
                    }
                }
                if (isCmd) {
                    cancel = true;
                    if (Settings.getConfig().getString("Settings.Worlds").contains(world)) {
                        if (!Settings.getConfig().getString("Settings.Bypass").contains(event.getPlayer().getUniqueId().toString())) {
                            if (GriefPreventionApi.canWorldEdit(event.getPlayer())) {
                                cancel = false;
                            }
                        } else {
                            cancel = false;
                        }
                    } else {
                        cancel = false;
                    }
                }
            }
            if (cancel) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Settings.getConfig().getString("Settings.DenyMsg")));
                event.setCancelled(true);
            }
        }
    }
}
