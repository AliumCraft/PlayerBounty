package com.aliumcraft.playerbounty.cmds.management;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.utils.Messages;

@SuppressWarnings("deprecation")
public class BountyAdd extends BountyBase {

	private Player commandSender;
	private String targetPlayerName;
	private int amount;
	private Player targetPlayer;
	private File f;
	private FileConfiguration c;
	private double playerBalance;
	
	public BountyAdd(Player sender, String args, int amount) {
		this.commandSender = sender;
		this.targetPlayerName = args;
		this.targetPlayer = Bukkit.getPlayer(args);
		this.amount = amount;
		playerBalance = Main.econ.getBalance(sender);
	}

	@Override
	public boolean checkForErrors() {		
		if(targetPlayer == null) {
			Messages.BountyAdd_Invalid.msg(commandSender);
			return false;
		}
		
		if(commandSender.getName().equalsIgnoreCase(targetPlayerName)) {
			Messages.BountyAdd_Self.msg(commandSender);
			return false;
		}
		
		if(this.playerBalance < this.amount) {
			Messages.BountyAdd_NotEnoughMoney.msg(commandSender);
			return false;
		}
		
		f = plugin.getBountyFile(targetPlayer);
		c = YamlConfiguration.loadConfiguration(f);
		
		int current = c.getInt("Amount");
		int sum = current + amount;
		int min = getMinBounty();
		
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
		
		return true;
	}

	@Override
	public void run() {	
		String msg;
		int current = c.getInt("Amount");
		
		if(current == 0) {
			current += amount;
			msg = Messages.BountyAdd_Set.toString();
			
			msg = msg.replace("{sender}", commandSender.getName());
			msg = msg.replace("{amount}", plugin.format(current));
			msg = msg.replace("{p}", targetPlayerName);
			
			Bukkit.broadcastMessage(msg);
		} else {
			current += amount;
			msg = Messages.BountyAdd_Inc.toString();
			
			msg = msg.replace("{sender}", commandSender.getName());
			msg = msg.replace("{amount}", plugin.format(current));
			msg = msg.replace("{p}", targetPlayerName);	
			
			if(amount <= plugin.getConfig().getInt("Settings.MinBountyToBroadcast")) {
				commandSender.sendMessage(msg);
			} else {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(msg);
				}
			}
		}
		
		c.set("Amount", current);
		c.set("HasBounty", true);
		Main.econ.withdrawPlayer(commandSender, amount);
		plugin.saveFile(f, c);
		
		msg = Messages.MoneyTaken.toString();
		msg = msg.replace("{amount}", plugin.format(amount));
		commandSender.sendMessage(msg);
	}
}
