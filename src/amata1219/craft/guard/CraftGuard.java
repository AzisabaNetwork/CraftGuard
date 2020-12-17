package amata1219.craft.guard;

import amata1219.craft.guard.registry.RegionRepositoryRegistry;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftGuard extends JavaPlugin implements CraftGuardAPI {

    private static CraftGuard instance;

    private RegionRepositoryRegistry regionRepositoryRegistry;

    @Override
    public void onEnable() {
        instance = this;
    }

    private void registerEventListeners(Listener... listeners) {
        for (Listener listener : listeners) getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public static CraftGuard instance() {
        return instance;
    }

    @Override
    public RegionRepositoryRegistry registry() {
        return regionRepositoryRegistry;
    }

}
