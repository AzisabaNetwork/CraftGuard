package amata1219.craft.guard.registry;

import amata1219.craft.guard.region.Region;
import amata1219.craft.guard.repository.RegionRepository;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;

public class RegionRepositoryRegistry {

    private final HashMap<World, RegionRepository> repositories = new HashMap<>();

    public RegionRepository register(World world, RegionRepository repository) {
        return repositories.put(world, repository);
    }

    public RegionRepository unregister(World world) {
        return repositories.remove(world);
    }

    public RegionRepository repository(World world) {
        return repositories.get(world);
    }

    public Region regionAt(Location loc) {
        RegionRepository repository = repositories.get(loc.getWorld());
        return repository == null ? null : repository.regionAt(loc.getBlockX(), loc.getBlockZ());
    }

}
