package com.aliumcraft.playerbounty.ymls;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.utils.Messages;

public class MessagesYML {
	
	private static Main plugin = Main.getInstance();
	
	public static void loadFile(File f) {
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		Messages[] arrayOfMessages = Messages.values();
		
		for(int i = 0; i < arrayOfMessages.length; i++) {
			Messages m = arrayOfMessages[i];
			
			c.addDefault(m.getPath(), m.getMessage());
		}
		
		c.addDefault("Messages.Help", (List<String>) Arrays.asList(
				"&e&m---------------&b&l PlayerBounty &e&m---------------",
				"&f/bounty get {player} &7- Gets the current bounty amount someone has on their head.",
				"&f/bounty list &7- Opens a GUI which displays all bounties.",
				"&f/bounty add {player} {amount} &7- Increases/sets a bounty on someone.",
				"&f/bounty timer &7- Displays the time left for your bounty to expire.",
				"&f/bounty streak {player} &7-Shows you what someone's bounty streak is.",
				"&c/bounty reload &7- Reloads all the yml files for this plugin.",
				"&c/bounty take {player} {amount} &7- Silently takes the amount from that players bounty.",
				"&c/bounty set {player} {amount} &7-Silently sets someone's bounty.",
				"&e&m---------------&b&l PlayerBounty &e&m---------------"));
		
		c.options().copyDefaults(true);
		
		plugin.saveFile(f, c);
	}
}
