package com.aliumcraft.playerbounty.cmds.management;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.api.BountyManagement;
import com.aliumcraft.playerbounty.utils.Messages;

@SuppressWarnings("deprecation")
public class BountyGet extends BountyBase {

	private String arguments;
	private OfflinePlayer argumentsPlayer;
	private Player commandSender;
	private BountyManagement bountyManagement;
	private File bountyFile;
	private double currentBountyAmount;
	
	public BountyGet(Player p, String args) {
		this.arguments = args;
		this.commandSender = p;
		this.argumentsPlayer = Bukkit.getOfflinePlayer(arguments);
		this.bountyManagement = new BountyManagement(argumentsPlayer);
		this.bountyFile = plugin.getBountyFile(argumentsPlayer);
	}

	@Override
	public boolean checkForErrors() {		
		if(!plugin.getConfig().getBoolean("Features.BountyGet")) {
			Messages.BountyGet_Disabled.msg(commandSender);
		}
		
		if(!bountyFile.exists() || bountyManagement.getBounty() <= 0) {
			String msg = Messages.BountyGet_NoBounty.toString();
			msg = msg.replace("{name}", argumentsPlayer.getName());
			
			commandSender.sendMessage(msg);
			return false;
		}
		
		currentBountyAmount = bountyManagement.getBounty();
		
		return true;
	}

	@Override
	public void run() {
		String msg = Messages.BountyGet_Get.toString();
		msg = msg.replace("{name}", argumentsPlayer.getName());
		msg = msg.replace("{amount}", plugin.format(currentBountyAmount));
		commandSender.sendMessage(msg);		
	}
	
}
