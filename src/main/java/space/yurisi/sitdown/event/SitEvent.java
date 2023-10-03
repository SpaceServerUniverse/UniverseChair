package space.yurisi.sitdown.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SitEvent implements Listener {

    
    @EventHandler
    public void onPlayerSit(PlayerInteractEvent event) {
        if(event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            if (block.getType().toString().contains("STAIRS")) {
                if(!player.isSneaking() && player.getVehicle() != null) {
                    player.getVehicle().remove();
                    return;
                }
                Entity arrow = sitdown(block, player);
                arrow.addPassenger(player);
            }
        }
        if(event.getPlayer().isInsideVehicle() && event.getPlayer().isSneaking()) {
            event.getPlayer().sendMessage("event called");
            Entity vehicle = event.getPlayer().getVehicle();
            if(vehicle != null && vehicle instanceof Item) {
                vehicle.remove();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Entity vehicle = event.getPlayer().getVehicle();
        if(vehicle != null && vehicle instanceof Item) {
            vehicle.remove();
        }
    }

    private Entity sitdown(Block block, Player player) {
        Location location = block.getLocation();
        Arrow arrow = location.getWorld().spawnArrow(location, new Vector(0, 1, 0), 0, 0);
        arrow.setGravity(false);
        arrow.setSilent(true);
        arrow.setInvulnerable(true);
        arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
        return arrow;
    }
}
