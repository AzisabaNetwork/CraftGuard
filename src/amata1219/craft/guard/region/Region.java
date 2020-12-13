package amata1219.craft.guard.region;

import org.bukkit.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public record Region(
        int id,
        RegionType type,
        World world,
        Location3d lesser,
        Location3d greater,
        UUID ownerUUID,
        HashSet<Ordinance> ordinances,
        HashMap<UUID, ShareLevel> shareLevels
) {

    public boolean contains(int x, int y, int z) {
        return lesser.x() <= x && lesser.z() <= z && x <= greater.x() && z <= greater.z() && lesser.y() <= y && y <= greater.y();
    }

    public boolean isSatisfiedWithPlayerShareLevel(UUID playerUUID, ShareLevel required) {
        ShareLevel level = shareLevels.get(playerUUID);
        if (level == null) return false;
        return level.ordinal() >= required.ordinal();
    }

}
