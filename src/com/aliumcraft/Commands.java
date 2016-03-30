package com.aliumcraft;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Commands implements Listener, CommandExecutor {
	Main plugin;

	public Commands(Main inst1) {
		this.plugin = inst1;
	}


	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if (!(sender instanceof Player)) 
		{
			sender.sendMessage("This is plugin only supports players!");
			return true;
		}
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("bounty")) 
		{
			if(p.hasPermission("pbounty.default") || (p.hasPermission("pbounty.admin"))) {
				if (args.length == 0) {
					plugin.msg(p, "");
					plugin.msg(p, "&8&m-----------------+&r &b&lPlayer Bounty &8&m+------------------");
					plugin.msg(p, "&e/bounty list");
					plugin.msg(p, "&fView all players that have bounties.");
					plugin.msg(p, "");
					plugin.msg(p, "&e/bounty add <amount> <player>");
					plugin.msg(p, "&fPut a bounty on a player.");
					plugin.msg(p, "");
					plugin.msg(p, "&e/bounty get <player>");
					plugin.msg(p, "&fDisplay the bounty on specific player.");
					plugin.msg(p, "");
					
					if(p.hasPermission("pbounty.admin")) {
						plugin.msg(p, "&c/bounty reload");
						plugin.msg(p, "&fReloads the plugin's config.");
						plugin.msg(p, "");
					}
					
					plugin.msg(p, "&8&m-----------------+&r &b&lPlayer Bounty &8&m+------------------");
				} else if (args[0].equalsIgnoreCase("list")) {
					if (args.length == 1)
					{
						p.openInventory(plugin.bl.InventoryItems(0, p));
						plugin.msg(p, plugin.getString("Messages.Opening-List"));
					} else {
						plugin.msg(p, plugin.getString("Messages.Incorrect-Usage"));
					}
				} else if (args[0].equalsIgnoreCase("get")) {
					if (args.length == 1) 
					{
						plugin.msg(p, "&cIncorrect usage! /bounty get <player>");
					} else if (args.length == 2) {
						Player pl = p.getServer().getPlayer(args[1]);
						
						if(pl != null) {
							if(plugin.getConfig().contains("Bounties." + pl.getUniqueId())) {
								plugin.msg(p, plugin.getString("Messages.Bounty-Get")
										.replace("%bounty%", pl.getName())
										.replace("%amount%", NumberFormatting.format(Double.valueOf(plugin.getString("Bounties." + pl.getUniqueId())))));
							} else {
								plugin.msg(p, plugin.getString("Messages.Bounty-Get-Null")
										.replace("%bounty%", args[1]));
							}
						} else {
							plugin.msg(p, plugin.getString("Messages.Bounty-Get-Null-Player")
									.replace("%bounty%", args[1]));
						}
					}
						
				} else if (args[0].equalsIgnoreCase("add")) {
					if (args.length == 1) 
					{
						plugin.msg(p, "&cIncorrect usage! /bounty add <amount> <player>");
					} else if (args.length == 2) {
						plugin.msg(p, "&cIncorrect usage! /bounty add " + NumberFormatting.format(Double.valueOf(args[1])) + " <player>");
					} else if (args.length == 3) {
						try 
						{
							if(Double.valueOf(args[1]) >= plugin.getConfig().getDouble("Bounty.Min-Bounty-Increase")) {
								List<String> inv = plugin.getStrings("BountyList");
								Player z = p.getServer().getPlayer(args[2]);
								
								if(z != null) {
									if(z.getName() != p.getName()) {
										if(Main.econ.has(p.getName(), Double.valueOf(args[1]))) {
											Main.econ.withdrawPlayer(p, roundTwoDecimals(Double.valueOf(args[1])));
											
											if(!inv.contains(z.getName())) {
												inv.add(z.getName());
												plugin.getConfig().set("BountyList", inv);
												plugin.saveConfig();
											}
											
											plugin.msg(p, plugin.getString("Messages.Money-Taken")
													.replace("%amount%", NumberFormatting.format(Double.valueOf(args[1]))));
											if(!plugin.getConfig().contains("Bounties." + z.getUniqueId())) {
												plugin.getConfig().set("Bounties." + z.getUniqueId(), roundTwoDecimals(Double.valueOf(args[1])));
												plugin.saveConfig();
												
												plugin.bct(plugin.getString("Messages.Bounty-Set")
														.replace("%amount%", NumberFormatting.format(Double.valueOf(args[1])))
														.replace("%bounty%", z.getName()));
											} else {
												double current = plugin.getConfig().getDouble("Bounties." + z.getUniqueId());
												double addition = Double.parseDouble(args[1]);
												double sum = current + addition;
												
												plugin.getConfig().set("Bounties." + z.getUniqueId(), roundTwoDecimals(sum));
												plugin.saveConfig();
												
												plugin.bct(plugin.getString("Messages.Bounty-Set")
														.replace("%amount%", NumberFormatting.format(sum))
														.replace("%bounty%", z.getName()));
											}
										} else {
											plugin.msg(p, plugin.getString("Messages.Not-Enough-Balance"));
										} 
									} else {
										plugin.msg(p, plugin.getString("Messages.Cannot-Bounty-Self"));
									}
								} else {
									plugin.msg(p, plugin.getString("Messages.Bounty-Get-Null-Player")
											.replace("%bounty%", args[2]));
								}
							} else {
								plugin.msg(p, plugin.getString("Messages.Min-Bounty")
										.replace("%amount%", NumberFormatting.format(Double.valueOf(plugin.getConfig().getDouble("Bounty.Min-Bounty-Increase")))));
							}											
						} catch (NumberFormatException e) {
							plugin.msg(p, plugin.getConfig().getString("Messages.Incorrect-Usage"));
						}
					}
				} else if(p.hasPermission("pbounty.admin")) {
					if(args[0].equalsIgnoreCase("reload")) {
						plugin.reloadConfig();
						plugin.msg(p, plugin.getString("Messages.Reload"));
					}	
				}
			} else {
				plugin.msg(p, plugin.getString("Messages.NoPermission"));
			} 
		}

		return false;
	}
	
	public double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
 
    }

}
