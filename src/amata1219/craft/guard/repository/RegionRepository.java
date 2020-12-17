package amata1219.craft.guard.repository;

import amata1219.craft.guard.chunk.ChunkHashing;
import amata1219.craft.guard.chunk.ChunkMap;
import amata1219.craft.guard.region.ParentRegion;
import org.bukkit.Chunk;

import java.util.List;
import java.util.function.Consumer;

public class RegionRepository {

    private final ChunkMap<ParentRegion> state = new ChunkMap<>();

    public void add(ParentRegion region) {
        perform(region, hash -> state.put(hash, region));
    }

    public void remove(ParentRegion region) {
        perform(region, hash -> state.remove(hash, region));
    }

    private void perform(ParentRegion region, Consumer<Long> action) {
        Chunk lesser = region.lesser.chunkIn(region.world);
        Chunk greater = region.greater.chunkIn(region.world);
        for (int x = lesser.getX(); x <= greater.getX(); x ++)
            for (int z = lesser.getZ(); z <= greater.getZ(); z++) action.accept(ChunkHashing.hash(x, z));
    }

    public ParentRegion regionAt(int x, int y, int z) {
        List<ParentRegion> regions = state.get(ChunkHashing.hash(x, z));
        for (ParentRegion region : regions)
            if (region.containsLocation(x, y, z)) return region;

        return null;
    }

}
