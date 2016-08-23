package com.aliumcraft.playerbounty.cmds.management;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.utils.Messages;

@SuppressWarnings("deprecation")
public class BountyGet extends BountyBase {

	private String argPlayer;
	private OfflinePlayer argP;
	private Player commandSender;
	private File f;
	private FileConfiguration c;
	
	public BountyGet(Player p, String args) {
		argPlayer = args;
		commandSender = p;
		argP = Bukkit.getOfflinePlayer(argPlayer);
		f = plugin.getBountyFile(argP);
		c = YamlConfiguration.loadConfiguration(f);
	}

	@Override
	public boolean checkForErrors() {
		File f = plugin.getBountyFile(argP);
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		
		if(!plugin.getConfig().getBoolean("Features.BountyGet")) {
			Messages.BountyGet_Disabled.msg(commandSender);
		}
		
		if(!f.exists() || c.getInt("Amount") <= 0) {
			String msg = Messages.BountyGet_NoBounty.toString();
			msg = msg.replace("{name}", argPlayer);
			
			commandSender.sendMessage(msg);
			return false;
		}
		
		return true;
	}

	@Override
	public void run() {
		String msg = Messages.BountyGet_Get.toString();
		msg = msg.replace("{name}", argPlayer);
		msg = msg.replace("{amount}", plugin.format(c.getInt("Amount")));
		commandSender.sendMessage(msg);		
	}
	
}
