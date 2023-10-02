package space.yurisi.sitdown.event;

import org.bukkit.Location;
import org.bukkit.Material;
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
                Item item = sitdown(block, player);
                item.setItemStack(new ItemStack(Material.BIRCH_BUTTON));
                item.setPassenger(player);
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

    private Item sitdown(Block block, Player player) {
        Location location = block.getLocation().add(0.5, 0.2, 0.5);
        Item item = location.getWorld().dropItemNaturally(location, new ItemStack(Material.BIRCH_BUTTON));
        item.setPickupDelay(Integer.MAX_VALUE);
        item.teleport(location);
        item.setVelocity(new Vector(0, 0, 0));
        return item;
    }
}
