package amata1219.craft.guard.extension;

import amata1219.craft.guard.CraftGuard;
import amata1219.craft.guard.region.Region;
import amata1219.craft.guard.registry.RegionRepositoryRegistry;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class LazyRegion {

    public static Lazy<Region> of(Location loc) {
        return Lazy.of(() -> registry().regionAt(loc));
    }

    public static Lazy<Region> of(Block block) {
        return Lazy.of(() -> registry().regionAt(block.getLocation()));
    }

    public static Lazy<Region> of(Entity entity) {
        return Lazy.of(() -> registry().regionAt(entity.getLocation()));
    }

    private static RegionRepositoryRegistry registry() {
        return CraftGuard.instance().registry();
    }

}
