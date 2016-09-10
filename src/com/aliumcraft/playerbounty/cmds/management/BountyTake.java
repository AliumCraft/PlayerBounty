package com.aliumcraft.playerbounty.cmds.management;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.aliumcraft.playerbounty.api.BountyManagement;
import com.aliumcraft.playerbounty.utils.Messages;

@SuppressWarnings("deprecation")
public class BountyTake extends BountyBase {

	private CommandSender sender;
	private OfflinePlayer targetPlayer;
	private double amount;
	private BountyManagement bountyManagement;
	
	public BountyTake(CommandSender sender, String args, double amount) {
		this.sender = sender;
		this.targetPlayer = Bukkit.getOfflinePlayer(args);
		this.amount = amount;
	}
	
	@Override
	public boolean checkForErrors() {
		if(!targetPlayer.hasPlayedBefore()) {
			Messages.BountySet_HasntJoinedBefore.msg(sender);
			return false;
		}
		
		this.bountyManagement = new BountyManagement(targetPlayer);		
		return true;
	}

	@Override
	public void run() {
		this.bountyManagement.takeBounty(amount);
		
		String msg = Messages.BountyTake_Take.toString();
		msg = msg.replace("{bounty}", targetPlayer.getName());
		msg = msg.replace("{amount}", plugin.format(amount));
		
		Bukkit.broadcastMessage(msg);
	
		
	}

}
