package amata1219.craft.guard.repository;

import amata1219.craft.guard.chunk.ChunkHashing;
import amata1219.craft.guard.chunk.ChunkMap;
import amata1219.craft.guard.region.Region;
import org.bukkit.Chunk;

import java.util.List;
import java.util.function.Consumer;

public class RegionRepository {

    private final ChunkMap<Region> state = new ChunkMap<>();

    public void add(Region region) {
        perform(region, hash -> state.put(hash, region));
    }

    public void remove(Region region) {
        perform(region, hash -> state.remove(hash, region));
    }

    private void perform(Region region, Consumer<Long> action) {
        Chunk lesser = region.lesser().chunkIn(region.world());
        Chunk greater = region.greater().chunkIn(region.world());
        for (int x = lesser.getX(); x <= greater.getX(); x ++)
            for (int z = lesser.getZ(); z <= greater.getZ(); z++) action.accept(ChunkHashing.hash(x, z));
    }

    public Region regionAt(int x, int y, int z) {
        List<Region> regions = state.get(ChunkHashing.hash(x, z));
        for (Region region : regions)
            if (region.contains(x, y, z)) return region;

        return null;
    }

}
