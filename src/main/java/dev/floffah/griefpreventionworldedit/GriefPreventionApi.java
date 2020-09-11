package dev.floffah.griefpreventionworldedit;

import java.util.UUID;
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
        return canWorldEditLocation(p, p.getLocation());
    }
    
    public static boolean canWorldEditLocation(final Player p, final Location l) {
        boolean result = false;
        final Claim claim = GriefPreventionApi.ess.dataStore.getClaimAt(l, true, (Claim) null);
        
        if (claim == null || p == null) return false;
        
        if ((claim.ownerID != null && claim.ownerID.equals(p.getUniqueId())) || claim.getPermission(p.getUniqueId().toString()) == ClaimPermission.Build) {
            result = true;
        }
        return result;
    }
    
    public static Claim getClaimAt(final Location l) {
        return GriefPreventionApi.ess.dataStore.getClaimAt(l, true, (Claim) null);
    }
    
    public static ClaimPermission getPermission(final Player p) {
        ClaimPermission result = null;
        final Claim claim = GriefPreventionApi.ess.dataStore.getClaimAt(p.getLocation(), true, (Claim) null);
        if (claim != null) {
            result = claim.getPermission(p.getUniqueId().toString());
        }
        return result;
    }
    
    
    public static UUID getOwner(final Player p) {
        UUID result = null;
        final Claim claim = GriefPreventionApi.ess.dataStore.getClaimAt(p.getLocation(), true, (Claim) null);
        if (claim != null) {
            result = claim.ownerID;
        }
        return result;
    }
    
}