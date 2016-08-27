package de.mickyjou.paintballgun;

import me.mickyjou.plugins.gems.api.GemProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PaintballCommand implements CommandExecutor {

	File file = new File("plugins/PaintballGun", "config.yml");
	FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("paintballgun.buy")) {

				String gems = cfg.getString("gems");

				if (Bukkit.getServicesManager().isProvidedFor(GemProvider.class)) {
					GemProvider gemProvider = Bukkit.getServicesManager().getRegistration(GemProvider.class)
							.getProvider();
					if (!gemProvider.removeGems(((OfflinePlayer) sender), gems)) {
						sender.sendMessage(ChatColor.RED + "You don't have enough Gems to buy a paintball gun.");
						return true;
					}
				}

				ItemStack itemstack = new ItemStack(Material.DIAMOND_HOE);
				ItemMeta meta = itemstack.getItemMeta();
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Paintball Gun");
				itemstack.setItemMeta(meta);
				p.getInventory().addItem(itemstack);

				return true;
			} else {
				p.sendMessage(ChatColor.RED + "You don't have the permission to buy a PaintballGun.");
			}
		} else {
			return false;
		}
	}
}
