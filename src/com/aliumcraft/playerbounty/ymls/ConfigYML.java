package com.aliumcraft.playerbounty.ymls;

import org.bukkit.configuration.file.FileConfiguration;

import com.aliumcraft.playerbounty.Main;

public class ConfigYML {

	private static Main plugin = Main.getInstance();
	
	public static void loadConfig() {
		FileConfiguration c = plugin.getConfig();
		
		c.options().header("Settings is where you can configure everything while Features \n"
				+ "is where you enable or disable features on the plugin.");
		
		c.addDefault("Settings.MinBounty", 100);
		c.addDefault("Settings.MaxBounty", 10000000);
		
		c.addDefault("Features.BountyStreak", true);
	}
}
