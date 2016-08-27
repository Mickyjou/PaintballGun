package de.mickyjou.paintballgun;

import me.mickyjou.plugins.gems.api.GemProvider;

import java.util.List;

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
			if (args.length == 0) {
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
			} else if (args.length == 1) {
				if (p.hasPermission("paintballgun.list")) {
					if (args[0].equalsIgnoreCase("list")) {
						List<String> blockedBlocks = cfg.getStringList("BlockedBlocks");
						p.sendMessage(ChatColor.GRAY + "Blocked blocks: " + ChatColor.GOLD + blockedBlocks);
						return true;

					}
				} else {
					p.sendMessage(ChatColor.RED + "You don't have the permission to execute this command.");
				}
			} else if (args.length == 2) {
				if (p.hasPermission("paintballgun.admin")) {
					List<String> blockedBlocks = cfg.getStringList("BlockedBlocks");
					if (args[1].equalsIgnoreCase("add")) {
						blockedBlocks.add(args[2]);
						cfg.set("BlockedBlocks", blockedBlocks);
						cfg.save(file);
						p.sendMessage(ChatColor.GRAY + "You added the ID " + ChatColor.GOLD + args[2] + ChatColor.GRAY + " to the blocked Blocks." );
					} else if (args[1].equalsIgnoreCase("remove")) {
						blockedBlocks.remove(args[2]);
						cfg.set("BlockedBlocks", blockedBlocks);
						cfg.save(file);
						p.sendMessage(ChatColor.GRAY + "You removed the ID " + ChatColor.GOLD + args[2] + ChatColor.GRAY + " to the blocked Blocks." );
					}
				} else {
					p.sendMessage(ChatColor.RED + "You don't have the permission to execute this command.");
				}
			}
		} else {
			return false;
		}

	}
}
