package amata1219.craft.guard.region;

import org.bukkit.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public abstract class Region {

    public final int id;
    public final RegionType type;
    public final World world;
    public final Location3d lesser;
    public final Location3d greater;
    public final UUID ownerUUID;
    public final HashSet<Ordinance> ordinances;
    public final HashMap<UUID, ShareLevel> shareLevels;

    public Region(
            int id,
            RegionType type,
            World world,
            Location3d lesser,
            Location3d greater,
            UUID ownerUUID,
            HashSet<Ordinance> ordinances,
            HashMap<UUID, ShareLevel> shareLevels
    ) {
        this.id = id;
        this.type = type;
        this.world = world;
        this.lesser = lesser;
        this.greater = greater;
        this.ownerUUID = ownerUUID;
        this.ordinances = ordinances;

        shareLevels.put(ownerUUID, ShareLevel.L3);
        this.shareLevels = shareLevels;
    }

    public boolean containsLocation(int x, int y, int z) {
        return lesser.x() <= x && lesser.z() <= z && x <= greater.x() && z <= greater.z() && lesser.y() <= y && y <= greater.y();
    }

    public abstract boolean isEnactedOrdinance(Ordinance ordinance);

    public boolean isNotEnactedOrdinance(Ordinance ordinance) {
        return !isEnactedOrdinance(ordinance);
    }

    public abstract boolean isSatisfiedWithPlayerShareLevel(UUID playerUUID, ShareLevel required);

    public boolean isNotSatisfiedWithPlayerShareLevel(UUID playerUUID, ShareLevel required) {
        return !isSatisfiedWithPlayerShareLevel(playerUUID, required);
    }

}
