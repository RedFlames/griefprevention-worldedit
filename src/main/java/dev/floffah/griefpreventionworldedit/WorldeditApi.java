package dev.floffah.griefpreventionworldedit;

import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.IncompleteRegionException;

public class WorldeditApi {
    public static boolean usePlugin;
    public static WorldEditPlugin ess;

    static {
        WorldeditApi.usePlugin = false;
    }

    public static void init() {
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        final Plugin essPlugin = pluginManager.getPlugin("WorldEdit");
        if (essPlugin != null && essPlugin.isEnabled()) {
            WorldeditApi.ess = (WorldEditPlugin) essPlugin;
            WorldeditApi.usePlugin = true;
        }
    }
	
    public static Region getSelection(final Player p) {
        final LocalSession sess = ess.getSession(p);
		if (sess == null) {
			return null;
		}
		
		if (sess.isSelectionDefined(BukkitAdapter.adapt(p.getWorld()))) {
			try {
				return sess.getSelection(BukkitAdapter.adapt(p.getWorld()));
			} catch(IncompleteRegionException e) {
				return null;
			}
		}
		return null;
	}

    public static boolean selectionInGPClaim(final Player p, final Region sel) {
        boolean result = false;
		
        if (sel == null) {
            return true;
        }
		
		Location selMin = BukkitAdapter.adapt(p.getWorld(), sel.getMinimumPoint());
		Location selMax = BukkitAdapter.adapt(p.getWorld(), sel.getMaximumPoint());
		
		if ( GriefPreventionApi.canWorldEditLocation(p, selMin) 
		  && GriefPreventionApi.canWorldEditLocation(p, selMax)
	      && GriefPreventionApi.getClaimAt(selMin).getID() == GriefPreventionApi.getClaimAt(selMax).getID()) {
			  return true;
		  }
		
        return result;
    }
}