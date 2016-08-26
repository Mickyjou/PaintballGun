package de.mickyjou.paintballgun.listeners;

import de.mickyjou.paintballgun.PaintballGunPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerInteractListener implements Listener {
    private final PaintballGunPlugin plugin;
    int i = 1562;

    public PlayerInteractListener(PaintballGunPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDamage(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()) {
                if (e.getItem().getItemMeta().getDisplayName()
                        .equalsIgnoreCase(ChatColor.DARK_PURPLE + "Paintball Gun")) {
                    Player p = e.getPlayer();

                    if (e.getItem().getDurability() < 1561) {
                        Snowball sb = p.launchProjectile(Snowball.class);
                        sb.setMetadata("isPaintball", new FixedMetadataValue(plugin, true));
                        int durability = e.getItem().getDurability();
                        int newdurability = durability + 5;
                        e.getItem().setDurability((short) newdurability);

                    } else {
                        ItemStack itemstack = new ItemStack(Material.DIAMOND_HOE);
                        ItemMeta meta = itemstack.getItemMeta();
                        meta.setDisplayName(ChatColor.DARK_PURPLE + "Paintball Gun");
                        itemstack.setItemMeta(meta);
                        itemstack.setDurability((short) i);

                        if (e.getPlayer().getInventory().contains(itemstack)) {
                            e.getPlayer().getInventory().remove(itemstack);
                        } else {
                            i++;
                            if (e.getPlayer().getInventory().contains(itemstack)) {
                                e.getPlayer().getInventory().remove(itemstack);

                            } else {
                                i++;
                                if (e.getPlayer().getInventory().contains(itemstack)) {
                                    e.getPlayer().getInventory().remove(itemstack);

                                } else {
                                    i++;
                                    if (e.getPlayer().getInventory().contains(itemstack)) {
                                        e.getPlayer().getInventory().remove(itemstack);

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
