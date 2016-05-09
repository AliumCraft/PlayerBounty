package com.aliumcraft.playerbounty;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.util.BountyAPI;
import com.aliumcraft.playerbounty.util.BountyTimer;

public class Commands implements CommandExecutor {
	Main plugin;

	public Commands(Main inst1) {
		this.plugin = inst1;
	}


	@SuppressWarnings({ "deprecation"})
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
					for(String s : plugin.messages.getStringList("Messages.Help")) {
						plugin.msg(p, ChatColor.translateAlternateColorCodes('&', s));
					}
					
				} else if (args[0].equalsIgnoreCase("list")) {
					if(plugin.getConfig().getBoolean("Inventory.Enabled")) {
						if (args.length == 1)
						{
							p.openInventory(plugin.bl.InventoryItems(0, p));
							plugin.msg(p, plugin.getMessage("Messages.OpeningList"));
						} else {
							plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Usage"));
						}
					} else {
						plugin.msg(p, plugin.getMessage("Messages.BountyListDisabled"));
					}
					
				} else if (args[0].equalsIgnoreCase("get")) {
					if(!plugin.getConfig().getBoolean("Inventory.Enabled")) {
						if(args.length == 2) {
							Player pExact = p.getServer().getPlayerExact(args[1]);
							
							if(pExact != null) {
								plugin.msg(p, plugin.getMessage("Messages.BountyGet")
										.replace("%bounty%", pExact.getName())
										.replace("%amount%", NumberFormatting.format(BountyAPI.getBounty(pExact))));
							} else {
								OfflinePlayer pOffline = p.getServer().getOfflinePlayer(args[1]);
								
								plugin.msg(p, plugin.getMessage("Messages.BountyGet")
										.replace("%bounty%", pOffline.getName())
										.replace("%amount%", NumberFormatting.format(BountyAPI.getBounty(pOffline))));
							}
						} else {
							plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Get"));
						}
					} else {
						plugin.msg(p, plugin.getMessage("Messages.BountyGetDisabled"));
					}
									
				} else if (args[0].equalsIgnoreCase("add")) {
					if(args.length == 3) {
						try {
							if(Double.valueOf(args[2]) >= plugin.getConfig().getDouble("Bounty.Min-Bounty-Increase")) {
								List<String> inv = plugin.getBounty().getStringList("BountyList");
								Player z = p.getServer().getPlayer(args[1]);
								
								if(z != null) {
									if(z.getName() != p.getName()) {
										double amount = roundTwoDecimals(Double.valueOf(args[2]));
										if(Main.econ.has(p.getName(), amount)) {
											if(plugin.getConfig().getBoolean("Bounty.Expire")) {
												if(!BountyTimer.timeLeft.containsKey(z)) {
													Main.econ.withdrawPlayer(p, amount);
													
													if(!inv.contains(z.getName())) {
														inv.add(z.getName());
														plugin.getBounty().set("BountyList", inv);
														plugin.saveBounty();
													}
													
													plugin.msg(p, plugin.getMessage("Messages.MoneyTaken")
															.replace("%amount%", NumberFormatting.format(amount)));

													if(BountyAPI.getBounty(z) != 0) {
														if(plugin.getConfig().getBoolean("Enabled.BountyAddBroadcast")) {
															plugin.bct(plugin.getMessage("Messages.BountyAdd")
																	.replace("%player%", p.getName())
																	.replace("%amount%", NumberFormatting.format(BountyAPI.getBounty(z) + amount))
																	.replace("%bounty%", z.getName()));
														}
													} else {
														if(plugin.getConfig().getBoolean("Enabled.BountySetBroadcast")) {
															plugin.bct(plugin.getMessage("Messages.BountySet")
																	.replace("%amount%", NumberFormatting.format(amount))
																	.replace("%bounty%", z.getName()));
														}
													}
													
													BountyAPI.addBounty(z, roundTwoDecimals(amount));
													BountyTimer countdown = new BountyTimer(plugin);
													int time = plugin.getConfig().getInt("Bounty.ExpireTime");
													
													BountyTimer.timeLeft.put(z, time);
													if(BountyTimer.timeLeft.get(z) != null) {
														countdown.startCountdown(z, time, plugin.getMessage("Messages.BountyExpire"));
													}
													
													plugin.msg(z, plugin.getMessage("Messages.BountyExpire")
															.replace("#", NumberFormatting.timeFormat(BountyTimer.timeLeft.get(z))));
												} else {
													plugin.msg(p, plugin.getMessage("Messages.CannotBounty"));
												}
											} else {
												Main.econ.withdrawPlayer(p, amount);
												
												if(!inv.contains(z.getName())) {
													inv.add(z.getName());
													plugin.getBounty().set("BountyList", inv);
													plugin.saveBounty();
												}
												
												plugin.msg(p, plugin.getMessage("Messages.MoneyTaken")
														.replace("%amount%", NumberFormatting.format(amount)));

												if(BountyAPI.getBounty(z) != 0) {
													if(plugin.getConfig().getBoolean("Enabled.BountyAddBroadcast")) {
														plugin.bct(plugin.getMessage("Messages.BountyAdd")
																.replace("%player%", p.getName())
																.replace("%amount%", NumberFormatting.format(BountyAPI.getBounty(z) + amount))
																.replace("%bounty%", z.getName()));
													}
												} else {
													if(plugin.getConfig().getBoolean("Enabled.BountySetBroadcast")) {
														plugin.bct(plugin.getMessage("Messages.BountySet")
																.replace("%amount%", NumberFormatting.format(amount))
																.replace("%bounty%", z.getName()));
													}
												}
												
												BountyAPI.addBounty(z, roundTwoDecimals(amount));
											}
										} else {
											plugin.msg(p, plugin.getMessage("Messages.NotEnoughBalance"));
										}
									} else {
										plugin.msg(p, plugin.getMessage("Messages.CannotBountySelf"));
									}
								} else {
									plugin.msg(p, plugin.getMessage("Messages.BountyGetInvalidPlayer")
											.replace("%bounty%", args[1]));
								}
							} else {
								plugin.msg(p, plugin.getMessage("Messages.MinBounty")
										.replace("%amount%", NumberFormatting.format(plugin.getConfig().getDouble("Bounty.Min-Bounty-Increase"))));
							}
						} catch (NumberFormatException e) {
							plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Usage"));
						}
					} else {
						plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Add"));
					}
					
				} else if(args[0].equalsIgnoreCase("reload")) {
					if(p.hasPermission("bounty.admin")) {
						plugin.loadYamls();
						plugin.reloadConfig();
						plugin.msg(p, plugin.getMessage("Messages.Reload"));
					}	
					
				} else if(args[0].equalsIgnoreCase("take")) {
					if(p.hasPermission("bounty.admin")) {
						if(args.length == 3) {
							try {
								Player z = p.getServer().getPlayer(args[1]);
								
								if(z != null) {
									if(z.getName() != p.getName()) {
										double amount = roundTwoDecimals(Double.valueOf(args[2]));
										
										if(BountyAPI.getBounty(z) - amount > 0) {
											plugin.msg(p, plugin.getMessage("Messages.BountyTake")
													.replace("%amount%", NumberFormatting.format(amount))
													.replace("%bounty%", z.getName())
													.replace("%total%", NumberFormatting.format(BountyAPI.getBounty(z) - amount)));
											BountyAPI.takeBounty(z, amount);
										} else {
											plugin.msg(p, plugin.getMessage("Messages.BountyTake")
													.replace("%amount%", NumberFormatting.format(amount))
													.replace("%bounty%", z.getName())
													.replace("%total%", NumberFormatting.format(0D)));
											BountyAPI.setBounty(p, 0);
										}
									} else {
										plugin.msg(p, plugin.getMessage("Messages.CannotBountySelf"));
									}
								} else {
									plugin.msg(p, plugin.getMessage("Messages.BountyGetInvalidPlayer")
											.replace("%bounty%", args[1]));
								}
							} catch (NumberFormatException e) {
								plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Usage"));
							}
						} else {
							plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Take"));
						}
					} else {
						plugin.msg(p, plugin.getMessage("Messages.NoPermission"));
					}
					
				} else if(args[0].equalsIgnoreCase("set")) {
					if(p.hasPermission("bounty.admin")) {
						if(args.length == 3) {
							try {
								Player z = p.getServer().getPlayer(args[1]);
								double amount = roundTwoDecimals(Double.valueOf(args[2]));
								
								if(z != null) {
									plugin.msg(p, plugin.getMessage("Messages.BountySet")
											.replace("%amount%", NumberFormatting.format(amount))
											.replace("%bounty%", z.getName()));
									BountyAPI.setBounty(z, amount);
								} else {
									plugin.msg(p, plugin.getMessage("Messages.BountyGetInvalidPlayer")
											.replace("%bounty%", args[1]));
								}
							} catch (NumberFormatException e) {
								plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Usage"));
							}
						} else {
							plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Set"));
						}
					} else {
						plugin.msg(p, plugin.getMessage("Messages.NoPermission"));
					}
					
				} else if(args[0].equalsIgnoreCase("timer")) {
					if(p.hasPermission("bounty.timer")) {
						if(args.length == 1) {
							if(plugin.getConfig().getBoolean("Bounty.Expire")) {
								if(BountyTimer.timeLeft.containsKey(p)) {
									plugin.msg(p, plugin.getMessage("Messages.BountyExpire")
											.replace("#", NumberFormatting.timeFormat(BountyTimer.timeLeft.get(p))));
								} else {
									plugin.msg(p, plugin.getMessage("Messages.UnknownBountyTimer"));
								}
							} else {
								plugin.msg(p, plugin.getMessage("Messages.TimerNotEnabled"));
							}
						} else {
							plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Timer"));
						}
					} else {
						plugin.msg(p, plugin.getMessage("Messages.NoPermission"));
					}
					
				} else if(args[0].equalsIgnoreCase("streak")) {
					if(p.hasPermission("bounty.default")) {
						if(args.length == 1) {
							plugin.msg(p, plugin.getMessage("Messages.BountyStreakSelf")
									.replace("{x}", String.valueOf(BountyAPI.getBountyStreak(p))));
						} else if(args.length == 2) {
							Player z = Bukkit.getServer().getPlayer(args[1]);
							
							if(z != null) {
								plugin.msg(p, plugin.getMessage("Messages.BountyStreakSelf")
										.replace("{x}", String.valueOf(BountyAPI.getBountyStreak(z)))
										.replace("%player%", z.getName()));
							} else {
								plugin.msg(p, plugin.getMessage("Messages.BountyGetInvalidPlayer")
										.replace("%bounty%", args[1]));
								
							}
						} else {
							plugin.msg(p, plugin.getMessage("Messages.IncorrectUsage.Streak"));
						}
					}
				}
			} else {
				plugin.msg(p, plugin.getMessage("Messages.NoPermission"));
			} 
		}

		return false;
	}
	
	private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
