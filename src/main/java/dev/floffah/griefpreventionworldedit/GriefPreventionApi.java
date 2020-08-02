package dev.floffah.griefpreventionworldedit;

import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import me.ryanhamshire.GriefPrevention.*;

public class GriefPreventionApi {
    public static boolean usePlugin;
    public static GriefPrevention ess;

    static {
        GriefPreventionApi.usePlugin = false;
    }

    public static void init() {
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        final Plugin essPlugin = pluginManager.getPlugin("GriefPrevention");
        if (essPlugin != null && essPlugin.isEnabled()) {
            GriefPreventionApi.ess = (GriefPrevention) essPlugin;
            GriefPreventionApi.usePlugin = true;
        }
    }

    public static boolean canWorldEdit(final Player p) {
        boolean result = false;
        final Claim claim = GriefPreventionApi.ess.dataStore.getClaimAt(p.getLocation(), true, (Claim) null);
        if (claim != null && claim.getOwnerName().toLowerCase().equals(p.getName().toLowerCase())) {
            result = true;
        }
        return result;
    }
}