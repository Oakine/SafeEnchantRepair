package com.gmail.oakinea.SafeEnchantRepair;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.oakinea.Utils.Utils;

public class SafeEnchantRepair extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Utils.applyPermissionMessages(this);
	}
	
	
	@Override
	public void onDisable() { }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("repair") || cmd.getName().equalsIgnoreCase("ser_repair")) {
			
			if(sender instanceof Player) {
				
				ItemStack is = ((Player)sender).getItemInHand();
				if(is.getAmount() < 1) {
					sender.sendMessage(Utils.getErrorColor() + "There's no item in your hand to repair!");
				} else {
					int removes = 0;
					if(sender.hasPermission("SafeEnchantRepair.enchant")) {
						Map<Enchantment,Integer> enchants = is.getEnchantments();
						
						Iterator<Enchantment> it = enchants.keySet().iterator();
						
					
						
						while(it.hasNext()) {
							Enchantment ench = it.next();
							boolean remove = false;
							if(ench.getMaxLevel() < enchants.get(ench)) {
								is.removeEnchantment(ench);
								remove = true;
							} else if(!ench.getItemTarget().includes(is)) {
								is.removeEnchantment(ench);
								remove = true;
							} else {
							for(Enchantment e:is.getEnchantments().keySet()) {
									if(ench.conflictsWith(e) && !ench.equals(e)) {
										is.removeEnchantment(ench);
										remove = true;
									}
								}
							}
							if(remove) {
								removes++;
							}
						}
					}
					
					is.setDurability((short) 0);
					
					
					sender.sendMessage(ChatColor.GREEN + "Repaired!");
					if(removes > 0){
						sender.sendMessage(ChatColor.DARK_RED + "Removed " + removes + " illegal enchants while repairing!");
					}
				}
				
				
			} else {
				sender.sendMessage(Utils.getPlayerCommandOnlyMessage());
			}
			
			return true;
		}
		
		return false;
	}
	
	public void log(String log) {
		
		getLogger().info(log);
	}
	
}
