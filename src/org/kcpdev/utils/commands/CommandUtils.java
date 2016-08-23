package org.kcpdev.utils.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.utils.Messages;

public abstract interface CommandUtils extends CommandExecutor {
	
	public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);
	
	public default void sendHelpMessage(CommandSender sender) {
		List<String> list = Main.getInstance().getMessagesConfig().getStringList("Messages.Help");
		
		for(String s : list) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
	}
	
	public default boolean checkIfSenderIsPlayer(CommandSender p) {
		if(p instanceof Player) {
			return true;
		}
		
		Messages.MustBePlayer.msg(p);		
		return false;
	}
	
	@SuppressWarnings("unused")
	public default boolean checkIfStringIsNumber(String s) {
		try {
			int a = Integer.valueOf(s);
			
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
