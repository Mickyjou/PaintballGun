package de.mickyjou.paintballgun;

import de.mickyjou.paintballgun.listeners.PlayerInteractListener;
import de.mickyjou.paintballgun.listeners.ProjectileHitListener;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PaintballGunPlugin extends JavaPlugin {

	File file = new File("plugins/PaintballGun", "config.yml");
	FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	@Override
	public void onEnable() {
		fillConfig();
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

	private void fillConfig() {
		if (!file.exists()) {
			cfg.set("gems", 10);
			try {
				cfg.save(file);
			} catch (Exception e) {

			}
		}
	}
}
