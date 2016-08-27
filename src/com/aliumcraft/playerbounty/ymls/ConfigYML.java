package com.aliumcraft.playerbounty.ymls;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.aliumcraft.playerbounty.Main;

public class ConfigYML {

	private static Main plugin = Main.getInstance();
	
	public static void loadConfig() {
		FileConfiguration c = plugin.getConfig();
		
		c.options().header(
				  "\n"
				+ "PlayerBounty created by AliumCraft.\n"
				+ "\n"
				+ "Database: Input all info for a database here.\n"
				+ "Settings: where you can configure everything\n"
				+ "Features: where you enable or disable features on the plugin.\n"
				+ "HeadDrops: Customization towards heads dropping when bounties claimed.\n"
				+ "BountyStreak: Manage all the streak info in this section.\n");
		
		c.addDefault("Database.Enabled", false);
		c.addDefault("Database.Host", "localhost");
		c.addDefault("Database.Port", "3306");
		c.addDefault("Database.Name", "sql_bukkit");
		c.addDefault("Database.Table", "playerbounty");
		c.addDefault("Database.User", "root");
		c.addDefault("Database.Pass", "");
		
		c.addDefault("Settings.MinBounty", 100);
		c.addDefault("Settings.MaxBounty", 10000000);
		c.addDefault("Settings.MinBountyToBroadcast", 1000);
		c.addDefault("Settings.MinBountyIncrease", 100);
		c.addDefault("Settings.BountyExpire", true);
		c.addDefault("Settings.BountyExpireTime", 3600);
		
		c.addDefault("Features.BountyStreak", true);
		c.addDefault("Features.HeadDrops", true);
		c.addDefault("Features.BountyParticles", true);
		c.addDefault("Features.BountyGet", true);
		c.addDefault("Features.BountyGUI.Enabled", true);
		c.addDefault("Features.BountyGUI.ShowOffline", false);
		
		c.addDefault("HeadDrops.Name", "&fSkull of &b&l{p}");
		c.addDefault("HeadDrops.Lore", (List<String>) Arrays.asList(
				"&7Defeated by &f{k} &7on",
				"&f{d} &7with a(n)",
				"&f{w}",
				"&7to claim a bounty worth",
				"&a$&f{a}&7."));
		
		c.addDefault("BountyGUI.Name", "&9Bounties Page: &6&l{page}");
		loadItemStack(c, "Divider", "102", "&f", (List<String>) Arrays.asList(""));
		loadItemStack(c, "Heads", "397:3", "&fSkull of &b{p}", (List<String>) Arrays.asList(
				"&dAmount: &b${amount}",
				"&7Kill this player to collect the reward.",
				"",
				"{status}"));
		loadItemStack(c, "NextPage", "339", "&e&lNext Page ->", (List<String>) Arrays.asList(""));
		loadItemStack(c, "PrevPage", "339", "&e&l<- Previous Page", (List<String>) Arrays.asList(""));
		
		c.options().copyDefaults(true);
		
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	private static void loadItemStack(FileConfiguration c, String filePath, String type, String name, List<String> lore) {
		String path = "BountyGUI." + filePath + ".";
		
		c.addDefault(path + "Item", type);
		c.addDefault(path + "Name", name);
		c.addDefault(path + "Lore", lore);
	}
}
