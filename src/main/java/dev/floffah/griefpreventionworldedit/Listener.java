package dev.floffah.griefpreventionworldedit;

import java.util.UUID;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import com.sk89q.worldedit.regions.Region;
import me.ryanhamshire.GriefPrevention.*;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        if (GriefPreventionApi.usePlugin) {
            Settings.reloadConfig();
			FileConfiguration config = Settings.getConfig();
			
			Player player = event.getPlayer();
			String player_uuid = player.getUniqueId().toString();
			
            String world = player.getWorld().toString().toLowerCase();
            world = world.replace("craftworld{name=", "");
            world = world.replace("}", "");
			Region selection = WorldeditApi.getSelection(player);
			
            String cmd = event.getMessage().split(" ")[0].toLowerCase();
            cmd = cmd.replace("//", "");
            cmd = cmd.replace("/", "");
			
            boolean isCmd = false;
			for (String cm : config.getStringList("Settings.WorldEditCmds")) {
				if(cmd.startsWith(cm)) {
					isCmd = true;
				}
			}
			
            boolean isSelCmd = false;
			for (String cm : config.getStringList("Settings.RestrictRegionToClaim")) {
				if(cmd.startsWith(cm)) {
					isSelCmd = true;
				}
			}
			
            boolean cancel = false;
            if (!player.hasPermission("gpwe.nowe")) {
				if (config.getStringList("Settings.Worlds").contains(world)) {
					if (!config.getStringList("Settings.Bypass").contains(player_uuid)) {
						if (isCmd) {
							cancel = true;
							if (GriefPreventionApi.canWorldEdit(player)) {
								cancel = false;
							}
						}
						if (isSelCmd) {
							cancel = true;
							if (selection != null && WorldeditApi.selectionInGPClaim(player, selection)) {
								cancel = false;
							}
						}
						if (config.getBoolean("Settings.RestrictAll") && !isCmd && !isSelCmd) {
							cancel = true;
						}
					}
				}
            }
			
            if (cancel) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Settings.DenyMsg")));
                event.setCancelled(true);
            }
        }
    }
}
