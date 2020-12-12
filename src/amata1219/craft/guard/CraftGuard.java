package amata1219.craft.guard;

import org.bukkit.plugin.java.JavaPlugin;

public class CraftGuard extends JavaPlugin {

    private static CraftGuard instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public static CraftGuard instance() {
        return instance;
    }

}
