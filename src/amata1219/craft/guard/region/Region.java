package amata1219.craft.guard.region;

import org.bukkit.World;

import java.util.HashMap;
import java.util.UUID;

public record Region(
        int id,
        World world,
        Location3d lesser,
        Location3d greater,
        HashMap<UUID, ShareLevel> permissions
) {

    public boolean containsIgnoringYCoordinate(int x, int z) {
        return lesser.x() <= x && lesser.z() <= z
                && x <= greater.x() && z <= greater.z();
    }

    public boolean contains(int x, int y, int z) {
        return containsIgnoringYCoordinate(x, z) && lesser.y() <= y && y <= greater.y();
    }

}
