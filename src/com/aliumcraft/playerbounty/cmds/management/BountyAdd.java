package com.aliumcraft.playerbounty.cmds.management;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.api.BountyManagement;
import com.aliumcraft.playerbounty.utils.BountyTimer;
import com.aliumcraft.playerbounty.utils.Messages;

@SuppressWarnings("deprecation")
public class BountyAdd extends BountyBase {

	private Player commandSender;
	private double amount;
	private Player targetPlayer;
	private double playerBalance;
	private BountyManagement bm;
	private BountyTimer countdown = new BountyTimer();
	
	public BountyAdd(Player sender, String args, double amount) {
		this.commandSender = sender;
		this.targetPlayer = Bukkit.getPlayer(args);
		this.amount = amount;
		this.playerBalance = Main.econ.getBalance(sender);
	}

	@Override
	public boolean checkForErrors() {		
		if(targetPlayer == null) {
			Messages.BountyAdd_Invalid.msg(commandSender);
			return false;
		}
		
		if(commandSender.getName().equalsIgnoreCase(targetPlayer.getName())) {
			Messages.BountyAdd_Self.msg(commandSender);
			return false;
		}
		
		if(this.playerBalance < this.amount) {
			Messages.BountyAdd_NotEnoughMoney.msg(commandSender);
			return false;
		}
		
		this.bm = new BountyManagement(targetPlayer);
		
		double current = bm.getBounty();
		double sum = current + amount;
		double min = getMinBounty();
		
		if(sum >= getMaxBounty()) {
			Messages.BountyAdd_Max.msg(commandSender);
			return false;
		}
		
		if(amount < min) {
			String msg = Messages.BountyAdd_Min.toString();
			msg = msg.replace("{amount}", ""+min);
			commandSender.sendMessage(msg);
			
			return false;
		}
		
		int m = plugin.getConfig().getInt("Settings.MinBountyIncrease");
		
		if((current != 0) && (m > amount)) {
			String msg = Messages.BountyAdd_MinInc.toString();
			msg = msg.replace("{amount}", ""+m);
			
			commandSender.sendMessage(msg);
			return false;
		}
		
		if(!plugin.getConfig().getBoolean("Features.BountyTimer")) return true;
		
		int time = plugin.getConfig().getInt("Settings.BountyExpireTime");
		String msg = Messages.BountyTimer_Expired.toString();
		
		BountyTimer.timeLeft.put(targetPlayer, time);
		countdown.startCountdown(targetPlayer, time);
		msg = msg.replace("#", plugin.formatTime(time));
		targetPlayer.sendMessage(msg);
		
		return true;
	}

	@Override
	public void run() {	
		String msg;
		double current = this.bm.getBounty();
		
		if(current == 0) {
			current += amount;
			msg = Messages.BountyAdd_Set.toString();
			
			msg = msg.replace("{sender}", commandSender.getName());
			msg = msg.replace("{amount}", plugin.format(current));
			msg = msg.replace("{p}", targetPlayer.getName());
			
			Bukkit.broadcastMessage(msg);
		} else {
			current += amount;
			msg = Messages.BountyAdd_Inc.toString();
			
			msg = msg.replace("{sender}", commandSender.getName());
			msg = msg.replace("{amount}", plugin.format(current));
			msg = msg.replace("{p}", targetPlayer.getName());	
			
			if(amount <= plugin.getConfig().getInt("Settings.MinBountyToBroadcast")) {
				commandSender.sendMessage(msg);
			} else {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(msg);
				}
			}
		}
		
		bm.addBounty(amount);
		Main.econ.withdrawPlayer(commandSender, amount);
		
		msg = Messages.MoneyTaken.toString();
		msg = msg.replace("{amount}", plugin.format(amount));
		commandSender.sendMessage(msg);
	}
}
