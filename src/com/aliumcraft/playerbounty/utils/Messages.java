package com.aliumcraft.playerbounty.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public enum Messages {

	NoPermission("Messages.NoPermission", "&c&l(!) &7You do not have access to that command!"),
	MustBePlayer("Messages.MustBePlayer", "&c&l(!) &7You must be a player to use this command!"),
	MoneyTaken("Messages.MoneyTaken", "&c&l- ${amount}"),
	Reload("Messages.Reload", "&6&l(!) &7You have successfully reloaded all the files! &6&l(!)"),
	BountyGet_NoBounty("Messages.BountyGet.NoBounty", "&6&l(!) &7{name} has no bounty on their head. &6&l(!)"),
	BountyGet_Get("Messages.BountyGet.Get", "&6&l(!) &7{name} has a &f${amount} &7bounty on their head. &6&l(!)"),
	BountyGet_Disabled("Messages.BountyGet.Disabled", "&c&l(!) &7The bounty get feature is disabled! &c&l(!)"),
	BountyAdd_Invalid("Messages.BountyAdd.Invalid", "&c&l(!) &7The specified player cannot recieve a bounty!"),
	BountyAdd_Self("Messages.BountyAdd.Self", "&c&l(!) &7You cannot add a bounty to yourself!"),
	BountyAdd_Max("Messages.BountyAdd.Max", "&c&l(!) &7You cannot add any more to this players bounty!"),
	BountyAdd_Min("Messages.BountyAdd.Min", "&c&l(!) &7The minimum bounty you can add is &f${amount}!"),
	BountyAdd_Set("Messages.BountyAdd.Add", "&6&l(!) &7{sender} has set a bounty on {p} for &f${amount}! &6&l(!)"),
	BountyAdd_Inc("Messages.BountyAdd.Increase", "&6&l(!) &7{sender} has increased the bounty on {p} to &f${amount} &6&l(!)"),
	BountyAdd_MinInc("Messages.BountyAdd.MinIncrease", "&c&l(!) &7The minimum amount of money you can increase with is &f${amount}!"),
	BountyAdd_NotEnoughMoney("Messages.BountyAdd.NotEnoughMoney", "&c&l(!) &7You do not have enough money to add this bounty!"),
	BountyList_NoBounties("Messages.BountyList.NoBounties", "&c&l(!) &7There is no current bounties to display!");
	
	private String path;
	private String message;
	private static FileConfiguration LANG;
	
	private Messages(String path, String message) {
		this.path = path;
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String toString() {
		return ChatColor.translateAlternateColorCodes('&', LANG.getString(getPath()));
	}
	
	public static void setFile(FileConfiguration c) {
		LANG = c;
	}
	
	public void msg(CommandSender p) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', LANG.getString(getPath())));
	}
}
