package amata1219.craft.guard.region;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ChildRegion extends Region {

    public final ParentRegion parent;

    public ChildRegion(
            int id,
            Location3d lesser,
            Location3d greater,
            HashSet<Ordinance> ordinances,
            HashMap<UUID, ShareLevel> shareLevels,
            ParentRegion parent
    ){
        super(id, parent.type, parent.world, lesser, greater, parent.ownerUUID, ordinances, shareLevels);
        this.parent = parent;
    }

    @Override
    public boolean isEnactedOrdinance(Ordinance ordinance) {
        return parent.isEnactedOrdinance(ordinance) || ordinances.contains(ordinance);
    }

    @Override
    public boolean isSatisfiedWithPlayerShareLevel(UUID playerUUID, ShareLevel required) {
        if (parent.isSatisfiedWithPlayerShareLevel(playerUUID, required)) return true;
        ShareLevel level = shareLevels.get(playerUUID);
        if (level == null) return false;
        return level.ordinal() >= required.ordinal();
    }

}
