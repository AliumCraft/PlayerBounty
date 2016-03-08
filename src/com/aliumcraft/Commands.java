package com.aliumcraft;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Commands implements Listener, CommandExecutor {
	Main plugin;

	public Commands(Main pl) {
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("Paintball")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					plugin.msg(p, "&8&m---------------------+&r &6&lPaintball &8&m+--------------------");
					plugin.msg(p, "");
					plugin.msg(p, "&4Admin Commands:");
					plugin.msg(p, "&e/paintball create <name>");
					plugin.msg(p, "&fCreates an arena with that specific name.");
					plugin.msg(p, "");
					plugin.msg(p, "&e/paintball set <min/max> <arena> <amount>");
					plugin.msg(p, "&fSet the minimum and maximum players for that arena. Min MUST be smaller then max.");
					plugin.msg(p, "");
					plugin.msg(p, "&e/paintball setspawnpoint/ssp <arena> <blue/red>");
					plugin.msg(p, "&fSet a spawnpoint for that team in that arena.");
					plugin.msg(p, "");
					plugin.msg(p, "&e/paintball arena <name>");
					plugin.msg(p, "&fView all the info for that arena. &8(Coming soon)");
					plugin.msg(p, "");
					plugin.msg(p, "&8&m---------------------+&r &6&lPaintball &8&m+--------------------");
				} else {
					// Admin commands.
					if (p.hasPermission("paintball.admin")) {
						if (args[0].equalsIgnoreCase("create")) {
							if (args.length == 1) {
								plugin.msg(p, "&cInvalid usage! Use /paintball create <name>");
							} else {
								if (plugin.getConfig().contains("Arenas." + args[1])) {
									plugin.msg(p, plugin.getS("Messages.Arena-exists"));
								} else {
									plugin.getConfig().set("Arenas." + args[1] + ".minPlayers",
											plugin.getConfig().getInt("Arena-Defaults.min-players"));
									plugin.getConfig().set("Arenas." + args[1] + ".maxPlayers",
											plugin.getConfig().getInt("Arena-Defaults.max-players"));
									plugin.getConfig().set("Arenas." + args[1] + ".Red", 
											plugin.getConfig().getStringList("Arena-Defaults.Red"));
									plugin.getConfig().set("Arenas." + args[1] + ".Blue", 
											plugin.getConfig().getStringList("Arena-Defaults.Blue"));
									plugin.getConfig().set("Arenas." + args[1] + ".Lobby.X", 
											plugin.getConfig().getDouble("Arena-Defaults.Lobby.X"));
									plugin.getConfig().set("Arenas." + args[1] + ".Lobby.Y", 
											plugin.getConfig().getDouble("Arena-Defaults.Lobby.Y"));
									plugin.getConfig().set("Arenas." + args[1] + ".Lobby.Z", 
											plugin.getConfig().getDouble("Arena-Defaults.Lobby.Z"));
									plugin.saveConfig();

									plugin.msg(p, plugin.getS("Messages.Arena-created").replace("%arena%", args[1]));
								}
							}
						} else if (args[0].equalsIgnoreCase("set")) {
							if (args.length <= 3) {
								plugin.msg(p, "&cInvalid usage! Use /paintball set <min/max/ss> <arena> <amount/team>");
							} else {
								if (args[1].equalsIgnoreCase("min") && args.length == 4) {
									if (plugin.getConfig().contains("Arenas." + args[2])) {
										if (Integer.valueOf(args[3]) < plugin.getConfig()
												.getInt("Arenas." + args[2] + ".maxPlayers")) {
											plugin.getConfig().set("Arenas." + args[2] + ".minPlayers",
													Integer.valueOf(args[3]));
											plugin.saveConfig();
											plugin.msg(p, plugin.getS("Messages.Min-Set").replace("%arena%", args[2])
													.replace("%amount%", args[3]));
										} else {
											plugin.msg(p, "&cThis value must be less then the max players!");
										}

									} else {
										plugin.msg(p,
												plugin.getS("Messages.Arena-NotExist").replace("%arena%", args[2]));
									}
								} else {
									if (args[1].equalsIgnoreCase("max") && args.length == 4) {
										if (plugin.getConfig().contains("Arenas." + args[2])) {
											if (Integer.valueOf(args[3]) > plugin.getConfig()
													.getInt("Arenas." + args[2] + ".minPlayers")) {
												plugin.getConfig().set("Arenas." + args[2] + ".maxPlayers",
														Integer.valueOf(args[3]));
												plugin.saveConfig();
												plugin.msg(p, plugin.getS("Messages.Max-Set")
														.replace("%arena%", args[2]).replace("%amount%", args[3]));
											} else {
												plugin.msg(p, "&cThis value must be more then the min players!");
											}

										} else {
											plugin.msg(p,
													plugin.getS("Messages.Arena-NotExist").replace("%arena%", args[2]));
										}
									}
								}
							}
						} else if (args[0].equalsIgnoreCase("setspawnpoint") || args[0].equalsIgnoreCase("ssp")) {
							if(args.length <= 2) {
								plugin.msg(p, "&c/paintball setspawnpoint/ssp <arena> <blue/red>");
							} else {
								List<String> red = plugin.getConfig().getStringList("Arenas." + args[1] + ".Red");
								List<String> blu = plugin.getConfig().getStringList("Arenas." + args[1] + ".Blue");
								
								if(plugin.getConfig().contains("Arenas." + args[1])) {
									if(args[2].equalsIgnoreCase("red")) {
										if(red.size() < plugin.getConfig().getInt("Arenas." + args[1] + ".maxPlayers") / 2) {
											plugin.saveLoc(p.getLocation(), args[1] + ".Red");
											plugin.saveConfig();
											plugin.msg(p, plugin.getS("Messages.Spawnpoint-Set")
													.replace("%team%", args[2])
													.replace("%arena%", args[1]));
										} else {
											plugin.msg(p, plugin.getS("Messages.Too-Many-Spawnpoints"));
										}
									} else {
										if(args[2].equalsIgnoreCase("blue")) {
											if(blu.size() < plugin.getConfig().getInt("Arenas." + args[1] + ".maxPlayers") / 2) {
												System.out.println("Check #9");
												plugin.saveLoc(p.getLocation(), args[1] + ".Blue");
												
												plugin.msg(p, plugin.getS("Messages.Spawnpoint-Set")
														.replace("%team%", args[2])
														.replace("%arena%", args[1]));
											} else {
												plugin.msg(p, plugin.getS("Messages.Invalid-Team"));
											}
										}
									}
									 
								} else {
									plugin.msg(p, plugin.getS("Messages.Arena-NotExist")
											.replace("%arena%", args[1]));
								}
							}
						}
					} else {
						plugin.msg(p, plugin.getS("Messages.No-permission"));
					}
				}
			}
		}
		return false;
	}

}
