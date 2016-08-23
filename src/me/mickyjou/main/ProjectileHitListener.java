package me.mickyjou.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

public class ProjectileHitListener implements Listener {

	private Snowball projectile;
	private Block hit;
	private int hitid;

	private HashMap<Integer, String> destroyed = new HashMap<>();
	private int curblock = 0;
	private int block = 0;
	private pbgun plugin;

	public ProjectileHitListener(pbgun pbgun) {
		this.plugin=pbgun;
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Snowball) {
			Snowball sb = (Snowball) e.getEntity();
			Player p = (Player) sb.getShooter();
			BlockIterator bi = new BlockIterator(sb.getWorld(), sb.getLocation().toVector(),
					sb.getVelocity().normalize(), 0.0D, 4);

			Block b = null;
			while (bi.hasNext()) {
				b = bi.next();

				if (b.getType() != Material.AIR) {
					break;
				}
			}
			

			destroyed.put(block, b.getLocation().getBlockX() + ";" + b.getLocation().getBlockY() + ";"
					+ b.getLocation().getBlockZ() + ";" + b.getTypeId() + ";" + b.getData());
			block++;
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {

				@Override
				public void run() {
					
					String[] parts = destroyed.get(curblock).split(";");
					int x = Integer.valueOf(parts[0]);
					int y= Integer.valueOf(parts[1]);
					int z= Integer.valueOf(parts[2]);
					int id= Integer.valueOf(parts[3]);
					byte data= Byte.valueOf(parts[4]);
					
					Location loc = new Location(sb.getWorld(), x, y, z);
					loc.getBlock().setTypeId(id);
					loc.getBlock().setData(data);
					curblock++;
					
					
				}
				
			}, 30*20);
			b.setType(Material.AIR);

		}
	}

}
