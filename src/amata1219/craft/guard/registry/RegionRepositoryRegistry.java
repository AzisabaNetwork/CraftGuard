package amata1219.craft.guard.registry;

import amata1219.craft.guard.chunk.ChunkHashing;
import amata1219.craft.guard.region.ChildRegion;
import amata1219.craft.guard.region.ParentRegion;
import amata1219.craft.guard.region.Region;
import amata1219.craft.guard.repository.RegionRepository;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;

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
        if (repository == null) return null;

        int x = loc.getBlockX(), y = loc.getBlockY(), z = loc.getBlockZ();
        ParentRegion parent = repository.regionAt(x, y, z);
        if (!parent.children.isEmpty()) return parent;

        List<ChildRegion> children = parent.children.get(ChunkHashing.hash(x, z));
        for (ChildRegion child : children)
            if (child.containsLocation(x, y, z)) return child;

        return parent;
    }

}
