package space.yurisi.sitdown;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.sitdown.event.EventListener;

import java.util.HashMap;
import java.util.UUID;

public final class Sitdown extends JavaPlugin {

    private final HashMap<UUID, Entity> vehicles = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public void setVehicle(Player player, Entity entity){
        this.vehicles.put(player.getUniqueId(), entity);
    }

    public Entity getVehicle(Player player){
        return this.vehicles.get(player.getUniqueId());
    }

    public void removeVehicle(Player player){
        this.vehicles.remove(player.getUniqueId());
    }

    public boolean existsVehicle(Player player){
        return this.vehicles.containsKey(player.getUniqueId());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
