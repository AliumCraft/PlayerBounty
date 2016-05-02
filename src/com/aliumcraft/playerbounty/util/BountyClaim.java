package com.aliumcraft.playerbounty.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.NumberFormatting;

public class BountyClaim implements Listener {
	Main plugin;
	
	public BountyClaim(Main pl) {
		this.plugin = pl;
	}
	
	@SuppressWarnings("deprecation")
	private void bountyClaim(Player killer, Player killed) {
		String amount = NumberFormatting.format(Double.valueOf(plugin.getConfig().getDouble("Bounties." + killed.getUniqueId())));
		Double amountD = plugin.getConfig().getDouble("Bounties." + killed.getUniqueId());
		List<String> bountyList = plugin.getConfig().getStringList("BountyList");
		
		if(plugin.getConfig().getDouble("Bounties." + killed.getUniqueId()) >=
				plugin.getConfig().getDouble("Bounty.Min-Bounty-Kill-Broadcast")) {
			for(String s : plugin.getMessages("Messages.BountyClaim")) {
				plugin.bct(ChatColor.translateAlternateColorCodes('&', s)
						.replace("%amount%", amount)
						.replace("%player%", killer.getName())
						.replace("%bounty%", killed.getName()));
			}
			plugin.msg(killer, plugin.getMessage("Messages.MoneyGiven")
					.replace("%amount%", amount));
		} else {
			plugin.msg(killer, plugin.getMessage("Messages.MoneyGiven")
					.replace("%amount%", amount));
		}
		Main.econ.depositPlayer(killer.getName(), amountD);
		
		plugin.getConfig().set("Bounties." + killed.getUniqueId(), null);
		bountyList.remove(killed.getName());
		plugin.getConfig().set("BountyList", bountyList);
		plugin.saveConfig();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onKillEvent(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		Entity killerr = e.getEntity().getKiller();
		
		if(killed.isDead()) {
			if((killerr instanceof Player)) {
				Player killer = (Player) e.getEntity().getKiller();
				
				if(killer.getName() != killed.getName()) {
					if((killer.hasPermission("pbounty.claim")) || (killer.hasPermission("pbounty.admin"))) {
						if(plugin.getConfig().contains("Bounties." + killed.getUniqueId())) {
							int amount = plugin.getConfig().getInt("Bounties." + killed.getUniqueId());
							String formatAmount = NumberFormatting.format(Double.valueOf(amount));
							bountyClaim(killer, killed);
							
							if(plugin.getConfig().getBoolean("HeadDrops.Drop")) {
								ItemStack skull = new ItemStack(397, 1, (short) 3);
								SkullMeta meta = (SkullMeta) skull.getItemMeta();
								List<String> lo = plugin.getStrings("HeadDrops.Lore");
								List<String> lore = new ArrayList<String>();
								Date date = new Date();
								SimpleDateFormat ft = new SimpleDateFormat("MMMMMMMMM d, yyyy");
								
								if(Bukkit.getBukkitVersion().startsWith("1.8") || (Bukkit.getBukkitVersion().startsWith("1.7"))) {
									for(String l : lo) {
										if(killer.getInventory().getItemInHand() != null) {
											String item = killer.getInventory().getItemInHand().getItemMeta().getDisplayName();
											
											if(item != null) {
												lore.add(ChatColor.translateAlternateColorCodes('&', l)
														.replace("%killer%", killer.getName())
														.replace("%date%", ft.format(date))
														.replace("%weapon%", item)
														.replace("%amount%", formatAmount));
											} else {
												Material nullItem = killer.getInventory().getItemInHand().getType();
												
												if(nullItem == Material.AIR) {
													lore.add(ChatColor.translateAlternateColorCodes('&', l)
															.replace("%killer%", killer.getName())
															.replace("%date%", ft.format(date))
															.replace("%weapon%", ChatColor.translateAlternateColorCodes('&', "&fBARE FIST(S)"))
															.replace("%amount%", formatAmount));
												} else {
													lore.add(ChatColor.translateAlternateColorCodes('&', l)
															.replace("%killer%", killer.getName())
															.replace("%date%", ft.format(date))
															.replace("%weapon%", ChatColor.translateAlternateColorCodes('&', "&f" + nullItem.toString())
																	.replace("_", " "))
															.replace("%amount%", formatAmount));
												}
											}
										}				
									}
								} else if (Bukkit.getBukkitVersion().startsWith("1.9")) {
									for(String l : lo) {
										if(killer.getInventory().getItemInMainHand() != null) {
											String item = killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
											
											if(item != null) {
												lore.add(ChatColor.translateAlternateColorCodes('&', l)
														.replace("%killer%", killer.getName())
														.replace("%date%", ft.format(date))
														.replace("%weapon%", item)
														.replace("%amount%", formatAmount));
											} else {
												Material nullItem = killer.getInventory().getItemInMainHand().getType();
												
												if(nullItem == Material.AIR) {
													lore.add(ChatColor.translateAlternateColorCodes('&', l)
															.replace("%killer%", killer.getName())
															.replace("%date%", ft.format(date))
															.replace("%weapon%", ChatColor.translateAlternateColorCodes('&', "&fBARE FIST(S)"))
															.replace("%amount%", formatAmount));
												} else {
													lore.add(ChatColor.translateAlternateColorCodes('&', l)
															.replace("%killer%", killer.getName())
															.replace("%date%", ft.format(date))
															.replace("%weapon%", ChatColor.translateAlternateColorCodes('&', "&f" + nullItem.toString())
																	.replace("_", " "))
															.replace("%amount%", formatAmount));
												}
											}
										}				
									}
								}
								
								
								
								meta.setOwner(killed.getName());
								meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getString("HeadDrops.Name")
										.replace("%player%", killed.getName())));
								meta.setLore(lore);
								skull.setItemMeta(meta);
								
								killed.getWorld().dropItem(killed.getLocation(), skull);
								lore.clear();
							}
						}			
					} else {
						plugin.msg(killer, plugin.getMessage("Messages.NoPermission-BountyClaim")
								.replace("%amount%", plugin.getConfig().getString("Bounties." + killed.getUniqueId())));
					}
				}
			}
		}
	}
}
