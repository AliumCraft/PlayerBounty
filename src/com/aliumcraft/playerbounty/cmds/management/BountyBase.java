package com.aliumcraft.playerbounty.cmds.management;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.utils.Messages;

public abstract class BountyBase {

	protected Main plugin = Main.getInstance();
	
	public abstract boolean checkForErrors();
	public abstract void run();
	
	public int getMaxBounty() {
		return plugin.getConfig().getInt("Settings.MaxBounty");
	}
	
	public int getMinBounty() {
		return plugin.getConfig().getInt("Settings.MinBounty");
	}
	
	public void sendHelpMessage(CommandSender sender) {
		List<String> list = Main.getInstance().getMessagesConfig().getStringList("Messages.Help");
		
		for(String s : list) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
	}
	
	public boolean checkIfSenderIsPlayer(CommandSender p) {
		if(p instanceof Player) {
			return true;
		}
		
		Messages.MustBePlayer.msg(p);		
		return false;
	}
	
	@SuppressWarnings("unused")
	public boolean checkIfStringIsNumber(String s) {
		try {
			int a = Integer.valueOf(s);
			
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
}
