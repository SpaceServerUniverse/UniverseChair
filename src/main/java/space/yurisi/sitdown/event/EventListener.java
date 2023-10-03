package space.yurisi.sitdown.event;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import space.yurisi.sitdown.Sitdown;

public class EventListener implements Listener {

    private final Sitdown main;

    public EventListener(Sitdown main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.hasBlock() && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (!block.getType().toString().contains("STAIRS")) {
            return;
        }

        if (!player.isSneaking() && player.getVehicle() != null) {
            return;
        }

        if(main.existsVehicle(player)){
            return;
        }

        sitDown(block, player);
    }

    @EventHandler
    public void onVehicle(PlayerToggleSneakEvent event) {
        exitVehicle(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        exitVehicle(event.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        exitVehicle(event.getPlayer());
    }

    private void sitDown(Block block, Player player) {
        Location location = block.getLocation();
        location.add(0.5, -1.1, 0.5);

        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setInvisible(true);
        armorStand.setGravity(false);
        armorStand.setSilent(true);
        armorStand.setInvulnerable(true);
        armorStand.addPassenger(player);

        main.setVehicle(player, armorStand);
    }

    private void exitVehicle(Player player) {
        if (!main.existsVehicle(player)) {
            return;
        }
        main.getVehicle(player).remove();
        main.removeVehicle(player);
    }
}
