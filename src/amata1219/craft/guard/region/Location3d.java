package amata1219.craft.guard.region;

import org.bukkit.Chunk;
import org.bukkit.World;

public record Location3d(int x, int y, int z) {

    public Chunk chunkIn(World world) {
        return world.getChunkAt(x, z);
    }

}
