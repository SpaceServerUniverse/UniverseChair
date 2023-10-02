package space.yurisi.sitdown;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.sitdown.event.SitEvent;

public final class Sitdown extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        SitEvent sitEvent = new SitEvent();
        getServer().getPluginManager().registerEvents(sitEvent, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
