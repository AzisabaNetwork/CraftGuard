package amata1219.craft.guard.listener;

import amata1219.craft.guard.Permission;
import amata1219.craft.guard.extension.PlayerExtension;
import amata1219.craft.guard.region.Ordinance;
import amata1219.craft.guard.region.Region;
import amata1219.craft.guard.region.ShareLevel;
import amata1219.craft.guard.registry.RegionRepositoryRegistry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

import static amata1219.craft.guard.Message.*;
import static amata1219.craft.guard.region.ShareLevel.*;

public class PlayerOnRegionListener implements Listener {

    private final RegionRepositoryRegistry registry;

    public PlayerOnRegionListener(RegionRepositoryRegistry registry) {
        this.registry = registry;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) on((PlayerInteractEntityEvent) event);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(isAdministrator(player)) return;

        Entity entity = event.getRightClicked();

        if (entity instanceof Tameable tameable) {
            if (tameable.isTamed()) {
                AnimalTamer tamer = tameable.getOwner();
                if (tamer != null) {
                    Region region = registry.regionAt(entity.getLocation());
                    if (region != null && !region.ownerUUID.equals(tamer.getUniqueId())) {
                        event.setCancelled(true);
                        player.sendMessage(CAN_NOT_USE);
                        return;
                    }
                }
            }
        }

        //フェンスに結び付けられたリードへのクリック時の挙動を確認する

        if (entity instanceof ArmorStand || entity instanceof Hanging)
            if (handlePlayerOnRegionEvent(event, entity, player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_CHANGE)) return;

        if ((entity instanceof Vehicle && entity instanceof InventoryHolder) || entity instanceof Villager)
            if (handlePlayerOnRegionEvent(event, entity, player, EDITING_CONTAINER_BLOCKS_INVENTORIES, CAN_NOT_OPEN)) return;

        if (entity instanceof Animals || entity instanceof Fish || (entity instanceof Creature && PlayerExtension.itemStackInPlayerHand(player, event.getHand()).getType() == Material.LEAD))
            handlePlayerOnRegionEvent(event, entity, player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_USE);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(isAdministrator(player)) return;

        Entity entity = event.getCaught();
        if (entity instanceof ArmorStand || entity instanceof Animals)
            handlePlayerOnRegionEvent(event, entity, player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_USE);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if(isAdministrator(player)) return;

        handlePlayerOnRegionEvent(event, event.getBlock(), player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_USE);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        if(isAdministrator(player)) return;

        handlePlayerOnRegionEvent(event, event.getBlockClicked(), player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_USE);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(PlayerTakeLecternBookEvent event) {
        Player player = event.getPlayer();
        if(isAdministrator(player)) return;

        handlePlayerOnRegionEvent(event, event.getLectern().getLocation(), player, EDITING_CONTAINER_BLOCKS_INVENTORIES, CAN_NOT_CHANGE);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action == Action.LEFT_CLICK_AIR) return;

        Player player = event.getPlayer();
        if(isAdministrator(player)) return;

        Block block = event.getClickedBlock();
        Material type = block == null ? Material.AIR : block.getType();

        switch (action) {
            case PHYSICAL -> {
                if (type == Material.TURTLE_EGG) handlePlayerOnRegionEvent(event, block, player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_BREAK);
            }
            case LEFT_CLICK_BLOCK -> {
                if (block == null) return;

                if (type == Material.FIRE || type == Material.DRAGON_EGG) handlePlayerOnRegionEvent(event, block, player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_CHANGE);
            }
            case RIGHT_CLICK_BLOCK -> {
                if (block == null) return;

                if (Tag.FENCE_GATES.isTagged(type) || Tag.WOODEN_TRAPDOORS.isTagged(type) || Tag.BUTTONS.isTagged(type) || type == Material.LEVER || type == Material.LECTERN)
                    if (handlePlayerOnRegionEvent(event, block, player, ACCESSING_REDPOWERISH_BLOCKS, CAN_NOT_USE)) return;

                if (isInventoryHolder(block) || CONTAINERS.contains(type))
                    if (handlePlayerOnRegionEvent(event, block, player, EDITING_CONTAINER_BLOCKS_INVENTORIES, CAN_NOT_OPEN)) return;

                if (RIGHT_CLICK_BLOCK_WATCH_LIST.contains(type))
                    if (handlePlayerOnRegionEvent(event, block, player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_CHANGE)) return;

                Material typeOfItemInHand = PlayerExtension.itemStackInPlayerHand(player, event.getHand()).getType();
                if (RIGHT_CLICK_ITEM_WATCH_LIST.contains(typeOfItemInHand))
                    if (handlePlayerOnRegionEvent(event, block, player, EDITING_BLOCKS_AND_ENTITIES, CAN_NOT_USE)) return;

                if (MINECART_LIST.contains(typeOfItemInHand)) {
                    Region region = registry.regionAt(block.getLocation());
                    if (region != null && region.isEnactedOrdinance(Ordinance.NOT_ALLOWED_TO_PLACE_MINECARTS) && region.isNotSatisfiedWithPlayerShareLevel(player.getUniqueId(), EDITING_BLOCKS_AND_ENTITIES)) {
                        event.setCancelled(true);
                        player.sendMessage(CAN_NOT_PLACE);
                    }
                }

                if (BOAT_LIST.contains(typeOfItemInHand)) {

                }
            }
        }
    }

    private final HashMap<Material, Boolean> isInventoryHolderCache = new HashMap<>();

    private boolean isInventoryHolder(Block block) {
        Material type = block.getType();

        if (isInventoryHolderCache.containsKey(type)) return isInventoryHolderCache.get(type);

        boolean hasInventory = block.getState() instanceof InventoryHolder;
        isInventoryHolderCache.put(type, hasInventory);

        return hasInventory;
    }

    private static final Set<Material> CONTAINERS = Collections.unmodifiableSet(EnumSet.of(
            Material.ANVIL,
            Material.CHIPPED_ANVIL,
            Material.DAMAGED_ANVIL,
            Material.SWEET_BERRY_BUSH,
            Material.BEE_NEST,
            Material.BEEHIVE,
            Material.BEACON,
            Material.STONECUTTER,
            Material.GRINDSTONE,
            Material.CARTOGRAPHY_TABLE,
            Material.LOOM,
            Material.RESPAWN_ANCHOR,
            Material.JUKEBOX
    ));

    private static final Set<Material> RIGHT_CLICK_BLOCK_WATCH_LIST;
    private static final Set<Material> RIGHT_CLICK_ITEM_WATCH_LIST;
    private static final Set<Material> MINECART_LIST;
    private static final Set<Material> BOAT_LIST;

    static {
        RIGHT_CLICK_BLOCK_WATCH_LIST = Collections.unmodifiableSet(EnumSet.of(
                Material.NOTE_BLOCK,
                Material.REPEATER,
                Material.DRAGON_EGG,
                Material.DAYLIGHT_DETECTOR,
                Material.COMPARATOR,
                Material.FLOWER_POT,
                Material.CAKE,
                Material.CAULDRON
        ));

        Set<Material> rightClickItemWatchList = new HashSet<>();
        Set<Material> minecartList = new HashSet<>();
        Set<Material> boatList = new HashSet<>();
        for (Material type : Material.values()) {
            if (type.isLegacy()) continue;

            String name = type.name();
            if (name.endsWith("_SPAWN_EGG") || name.endsWith("_DYE")) rightClickItemWatchList.add(type);
            else if (name.endsWith("_MINECART")) minecartList.add(type);
            else if (name.endsWith("_BOAT")) boatList.add(type);
        }

        rightClickItemWatchList.addAll(Arrays.asList(
                Material.BONE_MEAL,
                Material.END_CRYSTAL,
                Material.ARMOR_STAND,
                Material.FLINT_AND_STEEL,
                Material.FIRE_CHARGE
        ));

        RIGHT_CLICK_ITEM_WATCH_LIST = Collections.unmodifiableSet(rightClickItemWatchList);
        MINECART_LIST = Collections.unmodifiableSet(minecartList);
        BOAT_LIST = Collections.unmodifiableSet(boatList);
    }

    private boolean handlePlayerOnRegionEvent(Cancellable event, Location loc, Player player, ShareLevel required, String error) {
        Region region = registry.regionAt(loc);
        if (region != null && region.isNotSatisfiedWithPlayerShareLevel(player.getUniqueId(), required)) {
            event.setCancelled(true);
            player.sendMessage(error);
            return true;
        }
        return false;
    }

    private boolean handlePlayerOnRegionEvent(Cancellable event, Entity entity, Player player, ShareLevel required, String error) {
        return handlePlayerOnRegionEvent(event, entity.getLocation(), player, required, error);
    }

    private boolean handlePlayerOnRegionEvent(Cancellable event, Block block, Player player, ShareLevel required, String error) {
        return handlePlayerOnRegionEvent(event, block.getLocation(), player, required, error);
    }

    private boolean isAdministrator(Player player) {
        return player.hasPermission(Permission.ADMIN);
    }

}
