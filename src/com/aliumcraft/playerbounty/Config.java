package com.aliumcraft.playerbounty;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import com.aliumcraft.playerbounty.Main;

public class Config {
	Main plugin;

	public Config(Main main) {
		this.plugin = main;
	}
	
	List<String> bountyList = (List<String>) Arrays.asList("PLAYER_1", "PLAYER_2", "PLAYER_3");
	List<String> heads = (List<String>) Arrays.asList("&dAmount: $&b%amount%", "&7Kill this player to collect the reward.", "", "%status%");
	List<String> headdrop = (List<String>) Arrays.asList("&7Defeated by &f%killer% &7on", "&f%date% &7with a(n)", "%weapon%", "&7to claim a bounty on their", "&7head worth &a$&f%amount%&7.");
	List<String> divider = (List<String>) Arrays.asList("");
	List<String> nextPage = (List<String>) Arrays.asList("");
	List<String> prevPage = (List<String>) Arrays.asList("");
	
	public void loadMyConfig() {
		FileConfiguration c = this.plugin.getConfig();
		c.options().header("PlayerBounty v" + this.plugin.getDescription().getVersion() + " Config File"
				+ "\ncreated by AliumCraft Dev Team."
				+ "\n#"
				+ "\nDatabase: Input all your information here for the database."
				+ "\nBounty: All things to do with the bounty itself."
				+ "\nBountyList: Used to get all the bounties in the GUI."
				+ "\nBounties: Used to save the actual bounty value on a player."
				+ "\n###########################################"
				);
		c.addDefault("Database.Enabled", false);
		c.addDefault("Database.HOST", "localhost");
		c.addDefault("Database.PORT", "3306");
		c.addDefault("Database.NAME", "sql_bukkit");
		c.addDefault("Database.TABLE", "playerbounty");
		c.addDefault("Database.USER", "root");
		c.addDefault("Database.PASS", "");
		
		c.addDefault("Bounty.Min-Bounty-Kill-Broadcast", Double.valueOf(150000));
		c.addDefault("Bounty.Min-Bounty-Increase", Double.valueOf(25000));
		c.addDefault("Bounty.Particles", Boolean.valueOf(true));
		
		c.addDefault("HeadDrops.Drop", Boolean.valueOf(true));
		c.addDefault("HeadDrops.Name", "&fSkull of &b&l%player%");
		c.addDefault("HeadDrops.Lore", headdrop);
		
		c.addDefault("Inventory.Name", "&9Bounties Page: &5&l%page%");
		c.addDefault("Inventory.Offline", Boolean.valueOf(false));
		c.addDefault("Inventory.Heads.Name", "&r&fSkull of &b%player%");
		c.addDefault("Inventory.Heads.Lore", heads);
		c.addDefault("Inventory.Divider.Name", "&4");
		c.addDefault("Inventory.Divider.Lore", divider);
		c.addDefault("Inventory.NextPage.Name", "&e&lNext Page ->");
		c.addDefault("Inventory.NextPage.Lore", nextPage);
		c.addDefault("Inventory.PrevPage.Name", "&e&l<- Previous Page");
		c.addDefault("Inventory.PrevPage.Lore", prevPage);		
		
		c.options().copyDefaults(true);
		this.plugin.saveConfig();
		this.plugin.reloadConfig();
	}
}
