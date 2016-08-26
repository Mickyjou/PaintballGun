package de.mickyjou.paintballgun;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PaintballCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        ItemStack itemstack = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Paintball Gun");
        itemstack.setDurability((short) 0);
        itemstack.setItemMeta(meta);
        p.getInventory().addItem(itemstack);


        return false;
    }

}
