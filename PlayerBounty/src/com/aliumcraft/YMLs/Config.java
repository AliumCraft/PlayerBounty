package com.aliumcraft.YMLs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import com.aliumcraft.Main;

public class Config implements Listener {
	Main plugin;

	public Config(Main main) {
		this.plugin = main;
	}

	List<String> bountyClaim = (List<String>) Arrays.asList("&7", "&8&l<&b&lPlayer&f&lBounty&8&l> &f%player% &bclaimed a &f$%amount% &bbounty on &f%bounty%!", "&7");
	List<String> regionList = (List<String>) Arrays.asList("REGION_NAME", "ANOTHER_REGION", "ALSO_ANOTHER_REGION");
	List<String> bountyList = (List<String>) Arrays.asList("PLAYER_1", "PLAYER_2", "PLAYER_3");
	List<String> heads = (List<String>) Arrays.asList("&dAmount: $&b%amount%", "&7Kill this player to collect the reward.", "", "%status%");

	public void loadMyConfig() {
		FileConfiguration c = this.plugin.getConfig();
		c.options().header("PlayerBounty v" + this.plugin.getDescription().getVersion() + " Config File"
				+ "\ncreated by AliumCraft Dev Team."
				+ "\n#"
				+ "\nMessages: All configurable messages."
				+ "\nBounty: All things to do with the bounty itself."
				+ "\nRegion: Select if the player must be within the worldguard section to claim the bounty."
				+ "\n###########################################"
				);
		c.addDefault("Messages.Bounty-Set",
				"&8&l<&b&lPlayer&f&lBounty&8&l> &f%bounty% &bnow has a &f$%amount% &bbounty on their head!");
		c.addDefault("Messages.Opening-List", "&8&l<&b&lPlayer&f&lBounty&8&l> &bOpening Bounty Hunter Contracts..");
		c.addDefault("Messages.Bounty-Get", "&8&l<&b&lPlayer&f&lBounty&8&l> &f%bounty% &bhas a &f$%amount% &bbounty.");
		c.addDefault("Messages.Bounty-Get-Null",
				"&8&l<&b&lPlayer&f&lBounty&8&l> &f%bounty% &bhas no bounty on their head.");
		c.addDefault("Messages.Bounty-Get-Null-Player", "&8&l<&b&lPlayer&f&lBounty&8&l> &f%bounty% &bis not an online player.");
		c.addDefault("Messages.Bounty-Claim", bountyClaim);
		c.addDefault("Messages.Money-Taken", "&c&l- $%amount%");
		c.addDefault("Messages.Incorrect-Usage", "&8&l<&b&lPlayer&f&lBounty&8&l> &cThat command was used incorrectly. Please check /bounty for more information.");
		c.addDefault("Messages.Min-Bounty", "&8&l<&b&lPlayer&f&lBounty&8&l> &bThe minimum bounty you can set on someone is &f$%amount%&b.");
		c.addDefault("Messages.Not-Enough-Balance", "&8&l<&b&lPlayer&f&lBounty&8&l> &cYou do not have enough money to set this bounty!");
		c.addDefault("Messages.Cannot-Bounty-Self", "&8&l<&b&lPlayer&f&lBounty&8&l> &cYou cannot set a bounty on yourself!");
		c.addDefault("Messages.Money-Given", "&a&l+ $%amount%");
		c.addDefault("Messages.False-Claim", "&8&l<&b&lPlayer&f&lBounty&8&l> &bYou would have collected a &f$%bounty% &bif you had killed that player in the specified zones.");
		
		c.addDefault("Bounty.Min-Bounty-Kill-Broadcast", Double.valueOf(150000));
		c.addDefault("Bounty.Min-Bounty-Increase", Double.valueOf(25000));
		
		c.addDefault("Region.Region-Claiming", Boolean.valueOf(true));
		c.addDefault("Region.Names", regionList);
		
		c.addDefault("Heads.Name", "&b&l%player%");
		c.addDefault("Heads.Lore", heads);
		
		c.addDefault("BountyList", bountyList);
		c.addDefault("Bounties.PLAYER_1.Amount", Integer.valueOf(50000));
		
		
		c.options().copyDefaults(true);
		this.plugin.saveConfig();
		this.plugin.reloadConfig();
	}
}
