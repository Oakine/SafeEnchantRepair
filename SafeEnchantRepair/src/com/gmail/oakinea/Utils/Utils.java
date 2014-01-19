package com.gmail.oakinea.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Utils {

	private Utils() { }
	
	
	public static ChatColor getErrorColor() {
		return ChatColor.RED;
	}
	
	public static String getPlayerCommandOnlyMessage() {
		return getErrorColor() + "This command can only be run by a player!";
	}
	
	public static String getNotEnoughArgumentsMessage() {
		return getErrorColor() + "Not enough arguments!";
	}
	
	public static String getTooManyArgumentsMessage() {
		return getErrorColor() + "Too many arguments!";
	}

	public static void applyPermissionMessages(JavaPlugin plugin) {
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(plugin.getResource("plugin.yml"));
		
		for(String cmd : config.getConfigurationSection("commands").getKeys(false)){
		  plugin.getCommand(cmd).setPermissionMessage(getErrorColor() + "You don't have permission to use that command!");
		}
	}
	
}
