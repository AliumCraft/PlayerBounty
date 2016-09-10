package com.aliumcraft.playerbounty.api;

import java.io.File;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.aliumcraft.playerbounty.Main;

public class BountyManagement {

	public BountyManagement(OfflinePlayer p) {
		this.bountyFile = plugin.getBountyFile(p);
		this.bountyConf = YamlConfiguration.loadConfiguration(bountyFile);
		this.currentBounty = bountyConf.getDouble("Amount");
		this.currentStreak = bountyConf.getInt("BountyStreak");
		this.bountiesClaimed = bountyConf.getInt("BountiesClaimed");
	}
	
	private Main plugin = Main.getInstance();
	private File bountyFile;
	private FileConfiguration bountyConf;
	private double currentBounty;
	private int currentStreak;
	private int bountiesClaimed;
	
	public void saveBounty() {
		plugin.saveFile(bountyFile, bountyConf);
	}
	
	public double getBounty() {
		currentBounty = bountyConf.getDouble("Amount");
		
		return currentBounty;
	}
	
	public int getCurrentStreak() {
		this.currentStreak = bountyConf.getInt("BountyStreak");
		
		return this.currentStreak;
	}
	
	public int getBountiesClaimed() {
		this.bountiesClaimed = bountyConf.getInt("BountiesClaimed");
		
		return bountiesClaimed;
	}
	
	public void setBounty(double amount) {
		bountyConf.set("HasBounty", true);
		bountyConf.set("Amount", amount);
		
		currentBounty = amount;
		saveBounty();
	}
	
	public void setStreak(int amount) {		
		bountyConf.set("BountyStreak", amount);
		
		currentStreak = amount;
		saveBounty();
	}
	
	public void setBountiesClaimed(int amount) {
		bountyConf.set("BountiesClaimed", amount);
		
		bountiesClaimed = amount;
		saveBounty();
	}
	
	public void takeBounty(double amount) {
		double newAmount = 0;
		
		if((currentBounty - amount) <= 0) {
			newAmount = (getBounty() - amount);
			
			bountyConf.set("HasBounty", false);
		} else {
			bountyConf.set("HasBounty", true);
		}
		
		bountyConf.set("Amount", newAmount);
		
		currentBounty = newAmount;
		saveBounty();
	}
	
	public void addBounty(double amount) {
		double newAmount = (getBounty() + amount);
		
		setBounty(newAmount);
	}
	
	public void addStreak(int amount) {
		int newAmount = (getCurrentStreak() + amount);
		
		setStreak(newAmount);
	}
	
	public void addBountiesClaimed(int amount) {
		int newAmount = (getBountiesClaimed() + amount);
		
		setBountiesClaimed(newAmount);
	}
	
	
}
