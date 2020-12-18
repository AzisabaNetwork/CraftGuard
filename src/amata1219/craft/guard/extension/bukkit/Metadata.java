package amata1219.craft.guard.extension.bukkit;

import amata1219.craft.guard.CraftGuard;
import org.bukkit.metadata.FixedMetadataValue;

public class Metadata {

    public static FixedMetadataValue of(Object data) {
        return new FixedMetadataValue(CraftGuard.instance(), data);
    }

}
