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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.NumberFormatting;

@SuppressWarnings("deprecation")
public class BountyClaim implements Listener {
	Main plugin;
	
	public BountyClaim(Main pl) {
		this.plugin = pl;
	}
	
	
	private ItemStack customItem(int type, int amount, byte data, String name, List<String> lore) {
		ItemStack item = new ItemStack(Material.getMaterial(type), amount, data);
		ItemMeta itemMeta = item.getItemMeta();
		
		if(name != "") {
			itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		}
		
		List<String> list = new ArrayList<String>();
		for(String s : lore) {
			list.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		
		itemMeta.setLore(list);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	private void bountyStreaks(Player p) {
		List<String> rewards = new ArrayList<String>();
		BountyAPI.addBountyStreak(p, 1);
		
		if(plugin.getConfig().contains("BountyStreak.Rewards." + BountyAPI.getBountyStreak(p))) {
			String path = "BountyStreak.Rewards." + BountyAPI.getBountyStreak(p) + ".";
			double cash = plugin.getConfig().getDouble(path + "Cash");
			
			if(cash != 0.00D) {
				Main.econ.depositPlayer(p, cash);
				plugin.msg(p, plugin.getMessage("Messages.MoneyGiven")
						.replace("%amount%", NumberFormatting.format(cash)));
			}
			
			for(String s : plugin.getConfig().getConfigurationSection(path + "Items").getKeys(false)) {
				rewards.add(s);
			}
			
			for(int i = 0; i < rewards.size(); i++) {
				int type = plugin.getConfig().getInt(path + "Items." + rewards.get(i) + ".Type");
				int amount = plugin.getConfig().getInt(path + "Items." + rewards.get(i) + ".Amount");
				byte data = (byte) plugin.getConfig().getInt(path + "Items." + rewards.get(i) + ".Data");
				String name = plugin.getConfig().getString(path + "Items." + rewards.get(i) + ".Name");
				List<String> lore = plugin.getConfig().getStringList(path + "Items." + rewards.get(i) + ".Lore");
				
				if(p.getInventory().firstEmpty() != -1) {
					p.getInventory().addItem(customItem(type, amount, data, name, lore));
				} else {
					p.getWorld().dropItemNaturally(p.getLocation(), customItem(type, amount, data, name, lore));
				}
			}
		}
		
		if(plugin.getConfig().contains("BountyStreak.Broadcast." + BountyAPI.getBountyStreak(p))) {
			String msg = plugin.getString("BountyStreak.Broadcast." + BountyAPI.getBountyStreak(p));
			
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg)
					.replace("%player%", p.getName()));
		}
	}
	
	private void bountyClaim(Player killer, Player killed) {
		String amount = NumberFormatting.format(BountyAPI.getBounty(killed));
		Double amountD = BountyAPI.getBounty(killed);
		List<String> bountyList = plugin.getBounty().getStringList("BountyList");
		
		if(amountD >= plugin.getConfig().getDouble("Bounty.Min-Bounty-Kill-Broadcast")) {
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
		
		bountyList.remove(killed.getName());
		BountyAPI.setBounty(killed, 0);
		BountyAPI.addBountiesClaimed(killer, 1);
		bountyStreaks(killer);
	}
	
	@EventHandler
	public void onKillEvent(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		Entity killerr = e.getEntity().getKiller();
		
		if(killed.isDead()) {
			if((killerr instanceof Player)) {
				Player killer = (Player) e.getEntity().getKiller();
				
				if(killer.getName() != killed.getName()) {
					if((killer.hasPermission("pbounty.claim")) || (killer.hasPermission("pbounty.admin"))) {
						if(BountyAPI.getBounty(killed) != 0) {
							double amount = BountyAPI.getBounty(killed);
							String formatAmount = NumberFormatting.format(amount);
							bountyClaim(killer, killed);
							
							if(plugin.getConfig().getBoolean("HeadDrops.Drop")) {
								String name = plugin.getConfig().getString("HeadDrops.Name")
										.replace("%player%", killed.getName());
								List<String> lo = plugin.getStrings("HeadDrops.Lore");
								List<String> lore = new ArrayList<String>();
								Date date = new Date();
								SimpleDateFormat ft = new SimpleDateFormat("MMMMMMMMM d, yyyy");
								
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
														.replace("%weapon%", "&fBARE FIST(S)")
														.replace("%amount%", formatAmount));
											} else {
												lore.add(ChatColor.translateAlternateColorCodes('&', l)
														.replace("%killer%", killer.getName())
														.replace("%date%", ft.format(date))
														.replace("%weapon%", "&f" + nullItem.toString()
																.replace("_", " "))
														.replace("%amount%", formatAmount));
											}
										}
									}
								}
								
								ItemStack skull = customItem(397, 1, (byte) 3, name, lore);
								SkullMeta meta = (SkullMeta) skull.getItemMeta();
								
								meta.setOwner(killed.getName());
								skull.setItemMeta(meta);
								
								killed.getWorld().dropItemNaturally(killed.getLocation(), skull);
								lore.clear();
							}

							BountyAPI.setBountyStreak(killed, 0);
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
