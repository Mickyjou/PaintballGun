package de.mickyjou.paintballgun.listeners;

import de.mickyjou.paintballgun.PaintballGunPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerInteractListener implements Listener {
    private final PaintballGunPlugin plugin;

    public PlayerInteractListener(PaintballGunPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDamage(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() &&
                    e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Paintball Gun")) {
                Player p = e.getPlayer();

                if (e.getItem().getDurability() < e.getItem().getType().getMaxDurability()) {
                    Snowball sb = p.launchProjectile(Snowball.class);
                    sb.setMetadata("isPaintball", new FixedMetadataValue(plugin, true));
                    e.getItem().setDurability((short) (e.getItem().getDurability() + 5));

                } else {
                    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
                    p.getInventory().removeItem(e.getItem());
                }
            }
        }
    }
}
