package de.mickyjou.paintballgun.listeners;

import de.mickyjou.paintballgun.PaintballGunPlugin;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BlockIterator;

import java.util.HashMap;
import java.util.Map;

public class ProjectileHitListener implements Listener {
    private final PaintballGunPlugin plugin;
    private final Map<Location, Byte> woolBlocks = new HashMap<>();

    public ProjectileHitListener(PaintballGunPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata("isPaintball") && e.getEntity().getShooter() instanceof Player) {
            Projectile sb = e.getEntity();

            final Block b = getHitBlock(sb);
            if (b != null && !woolBlocks.containsKey(b.getLocation())) {
                byte color = (byte) Math.floor(Math.random() * 15 + 1);
                for (Player player : b.getWorld().getPlayers()) {
                    //noinspection deprecation
                    player.sendBlockChange(b.getLocation(), Material.WOOL, color);
                }
                b.getLocation().getWorld().playEffect(b.getLocation().add(0.5, 0.5, 0.5), Effect.POTION_BREAK, new Potion(getPotionEffect(color)));
                woolBlocks.put(b.getLocation(), color);

                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    woolBlocks.remove(b.getLocation());

                    for (Player player : b.getWorld().getPlayers()) {
                        //noinspection deprecation
                        player.sendBlockChange(b.getLocation(), b.getType(), b.getData());
                    }
                }, 30 * 20);
            }
        }
    }

    private PotionType getPotionEffect(byte color) {
        switch (color) {
            case 1:
            case 4:
                return PotionType.FIRE_RESISTANCE;
            case 10:
            case 6:
            case 2:
                return PotionType.REGEN;
            case 3:
            case 9:
                return PotionType.SPEED;
            case 5:
                return PotionType.JUMP;
            case 7:
            case 8:
                return PotionType.INVISIBILITY;
            case 13:
                return PotionType.POISON;
            case 11:
                return PotionType.WATER_BREATHING;
            case 12:
                return PotionType.STRENGTH;
            case 14:
                return PotionType.INSTANT_HEAL;
            case 15:
                return PotionType.INSTANT_DAMAGE;
            default:
                return PotionType.WATER;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            woolBlocks.forEach((location, color) -> {
                if (location.getWorld().equals(event.getPlayer().getWorld())) {
                    if (player.isOnline()) {
                        //noinspection deprecation
                        player.sendBlockChange(location, Material.WOOL, color);
                    }
                }
            });
        }, 5 * 20);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        woolBlocks.forEach((location, color) -> {
            if (location.getWorld().equals(event.getPlayer().getWorld())) {
                //noinspection deprecation
                event.getPlayer().sendBlockChange(location, Material.WOOL, color);
            }
        });
    }

    private Block getHitBlock(Projectile sb) {
        BlockIterator bi = new BlockIterator(sb.getWorld(), sb.getLocation().toVector(), sb.getVelocity().normalize(), 0.0D, 4);

        Block b = null;
        while (bi.hasNext()) {
            b = bi.next();

            if (b.getType() != Material.AIR) {
                break;
            }
        }

        return b;
    }
}
