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
	BountyList_NoBounties("Messages.BountyList.NoBounties", "&c&l(!) &7There is no current bounties to display!"),
	BountySet_HasntJoinedBefore("Messages.BountySet.HasntJoinedBefore", "&c&l(!) &7That player has not joined the server before!"),
	BountySet_Set("Messages.BountySet.Set", "&6&l(!) &7{bounty}s bounty was set to &f${amount} &6&l(!)"),
	BountyTake_Take("Messages.BountyTake.Take", "&c&l(!) &7{bounty}s bounty was reduced to &f${amount} &6&l(!)"),
	MoneyGiven("Messages.MoneyGiven", "&a&l+ $%amount%"),
	BountyTimer_Expired("Messages.BountyTimer.Expire", "&c&l(!) &7Your bounty will expire in &f# &7minute(s). &c&l(!)"),
	BountyTimer_NotEnabled("Messages.BountyTimer.NotEnabled", "&c&l(!) &7The bounty timer feature is not enabled! &c&l(!)"),
	BountyTimer_NoBounty("Messages.BountyTimer.NoBounty", "&c&l(!) &7You do not have a bounty or a bounty timer!"),
	BountyRewards_Help("Messages.BountyRewards.Help", "&6&l(!) &7Use &f/bounty rewards [number] &7to get info on the rewards for that amount of streaks."),
	BountyRewards_None("Messages.BountyRewards.None", "&c&l(!) &7There is no rewards currently set for hitting that many bounty streaks."),
	BountyRewards_Header("Messages.BountyRewards.Header", "&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
	BountyRewards_Filler1("Messages.BountyRewards.Filler1", "&bBounty Rewards (&f{x}&b):"),
	BountyRewards_Filler2("Messages.BountyRewards.Filler2", "  &7Money: &f${amount}"),
	BountyRewards_Filler3("Messages.BountyRewards.Filler3", "  &7Items:"),
	BountyRewards_Filler4("Messages.BountyRewards.Filler4", "    &7- &f{am}x &7{item}(s)"),
	BountyRewards_Footer("Messages.BountyRewards.Footer", "&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
	BountyRewards_Spacer("Messages.BountyRewards.Spacer", "&7"),
	BountyStreaks_BroadcastMessage("Messages.BountyStreaksMessage", "&6&l(!) &f%player% &7has reached a bounty kill streak of &f{x}&7! &6&l(!)"),
	BountyClaim_InvalidArea("Messages.InvalidAreaToClaim", "&c&l(!) &eYou would claimed a bounty on &f%player% &efor &f$%amount% &ebut you did not kill them in a correct area!");
	
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
