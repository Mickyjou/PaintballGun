package de.mickyjou.paintballgun;

import de.mickyjou.paintballgun.listeners.PlayerInteractListener;
import de.mickyjou.paintballgun.listeners.ProjectileHitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PaintballGunPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
    }

    private void registerCommands() {
        de.mickyjou.paintballgun.PaintballCommand cpb = new PaintballCommand();
        getCommand("paintballgun").setExecutor(cpb);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(this), this);
    }
}
