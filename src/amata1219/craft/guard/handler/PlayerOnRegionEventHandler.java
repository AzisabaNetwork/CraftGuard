package amata1219.craft.guard.handler;

import amata1219.craft.guard.extension.Lazy;
import amata1219.craft.guard.region.Ordinance;
import amata1219.craft.guard.region.Region;
import amata1219.craft.guard.region.ShareLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class PlayerOnRegionEventHandler {

    public static boolean handlePlayerOnRegionEvent(Cancellable event, Lazy<Region> regionSupplier, Player player, ShareLevel required, String error) {
        Region region = regionSupplier.value();
        if (region != null && region.isNotSatisfiedWithPlayerShareLevel(player.getUniqueId(), required)) {
            event.setCancelled(true);
            player.sendMessage(error);
            return true;
        }
        return false;
    }

    public static boolean handlePlayerOnRegionEvent(Cancellable event, Lazy<Region> regionSupplier, Player player, Ordinance ordinance, ShareLevel required, String error) {
        Region region = regionSupplier.value();
        if (region != null && region.isEnactedOrdinance(ordinance) && region.isNotSatisfiedWithPlayerShareLevel(player.getUniqueId(), required)) {
            event.setCancelled(true);
            player.sendMessage(error);
            return true;
        }
        return false;
    }

}
