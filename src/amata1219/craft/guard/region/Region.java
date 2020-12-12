package amata1219.craft.guard.region;

import org.bukkit.World;

public record Region(World world, Location3d lesser, Location3d greater) {

    public boolean contains(int x, int y, int z) {
        return lesser.x() <= x && lesser.y() <= y && lesser.z() <= z
                && x <= greater.x() && y <= greater.y() && z <= greater.z();
    }

}
