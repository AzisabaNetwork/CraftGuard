package amata1219.craft.guard.extension;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerExtension {

    private static final ItemStack AIR = new ItemStack(Material.AIR);

    public static ItemStack itemStackInPlayerHand(Player player, EquipmentSlot slot) {
        PlayerInventory inventory = player.getInventory();
        return switch (slot) {
            case HAND -> inventory.getItemInMainHand();
            case OFF_HAND -> inventory.getItemInOffHand();
            default -> AIR;
        };
    }

}
