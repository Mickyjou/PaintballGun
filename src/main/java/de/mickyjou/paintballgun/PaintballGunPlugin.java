package de.mickyjou.paintballgun;

import org.bukkit.plugin.java.JavaPlugin;

public class PaintballGunPlugin extends JavaPlugin {

    public void onEnable() {
        registerCommands();
        registerEvents();
    }

    public void onDisable() {

    }

    private void registerCommands() {
        de.mickyjou.paintballgun.PaintballCommand cpb = new PaintballCommand();
        getCommand("paintballgun").setExecutor(cpb);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(this), this);
    }
}
