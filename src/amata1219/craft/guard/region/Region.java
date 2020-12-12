package amata1219.craft.guard.region;

import org.bukkit.World;

public record Region(World world, Location3d lesser, Location3d greater) {

    public boolean containsIgnoringYCoordinate(int x, int z) {
        return lesser.x() <= x && lesser.z() <= z
                && x <= greater.x() && z <= greater.z();
    }

    public boolean contains(int x, int y, int z) {
        return containsIgnoringYCoordinate(x, z) && lesser.y() <= y && y <= greater.y();
    }

}
