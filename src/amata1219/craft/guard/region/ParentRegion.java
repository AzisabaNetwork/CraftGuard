package amata1219.craft.guard.region;

import amata1219.craft.guard.chunk.ChunkMap;
import org.bukkit.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ParentRegion extends Region {

    public final ChunkMap<ChildRegion> children;

    public ParentRegion(
            int id,
            RegionType type,
            World world,
            Location3d lesser,
            Location3d greater,
            UUID ownerUUID,
            HashSet<Ordinance> ordinances,
            HashMap<UUID, ShareLevel> shareLevels,
            ChunkMap<ChildRegion> children
    ){
        super(id, type, world, lesser, greater, ownerUUID, ordinances, shareLevels);
        this.children = children;
    }

    @Override
    public boolean isEnactedOrdinance(Ordinance ordinance) {
        return ordinances.contains(ordinance);
    }

    @Override
    public boolean isSatisfiedWithPlayerShareLevel(UUID playerUUID, ShareLevel required) {
        ShareLevel level = shareLevels.get(playerUUID);
        if (level == null) return false;
        return level.ordinal() >= required.ordinal();
    }

}
