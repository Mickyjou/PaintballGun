package me.mickyjou.main;

import org.bukkit.plugin.java.JavaPlugin;

public class pbgun extends JavaPlugin{
	
	public void onEnable() {
		registerCommands();
		registerEvents();
	}
	
	public void onDisable() {
		
	}
	
	public void registerCommands() {
		PaintballCommand cpb = new PaintballCommand(); getCommand("paintballgun").setExecutor(cpb);
	}
	
	public void registerEvents(){
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHitListener(this), this);
	}

}
