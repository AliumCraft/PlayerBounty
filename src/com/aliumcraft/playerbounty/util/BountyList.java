package com.aliumcraft.playerbounty.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.NumberFormatting;

public class BountyList implements Listener {
	Main plugin;
	public BountyList(Main inst) {
		this.plugin = inst;
	}
	
	private static List<ItemStack> ALL = new ArrayList<ItemStack>();
	public final static int nextPage = 53;
	public final static int prevPage = 45;
	
	public static HashMap<Player, Integer> myPage = new HashMap<Player, Integer>();
	private HashMap<Player,Boolean> hasInvOpen = new HashMap<Player,Boolean>();
	
	@SuppressWarnings("deprecation")
	public Inventory InventoryItems(int page, Player p) {
		int x;
		List<String> configL = Main.getBounty().getStringList("BountyList");
		
		int pageNum = page +1;
		String pageTitle = plugin.getString("Inventory.Name").replace("%page%", String.valueOf(pageNum));
		String dividerName = plugin.getString("Inventory.Divider.Name");
		List<String> dividerLore = plugin.getStrings("Inventory.Divider.Lore");
		String nextPageName = plugin.getString("Inventory.NextPage.Name");
		List<String> nextPageLore = plugin.getStrings("Inventory.NextPage.Lore");
		String prevPageName = plugin.getString("Inventory.PrevPage.Name");
		List<String> prevPageLore = plugin.getStrings("Inventory.PrevPage.Lore");
		
		final Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', pageTitle));
		final ItemStack divi = Main.getItem(Material.THIN_GLASS, 1, 0, dividerName, dividerLore);
		final ItemStack next = Main.getItem(Material.PAPER, 1, 0, nextPageName, nextPageLore);
		final ItemStack prev = Main.getItem(Material.PAPER, 1, 0, prevPageName, prevPageLore);
		
		boolean lastPage = false;
		final int cols = 9;
		final int rows = 5;
		
		int currentSlot = 0;
		int size = configL.size();
		
		for(x=0; x < size; x++) {
			ItemStack skull = new ItemStack(397, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            List<String> lo = plugin.getStrings("Inventory.Heads.Lore");
            List<String> lore = new ArrayList<String>();
            Player z = plugin.getServer().getPlayer(configL.get(x));
            
            if(z == null) {
            	if(plugin.getConfig().getBoolean("Inventory.Offline")) {
            		OfflinePlayer pz = plugin.getServer().getOfflinePlayer(configL.get(x));
            		
            		if(BountyAPI.getBounty(pz) != 0) {
                    	
                    	meta.setOwner(pz.getName());
                    	meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getString("Inventory.Heads.Name")
                    			.replace("%player%", pz.getName())));
                    	
                    	for(String l : lo) {
                    		lore.add(ChatColor.translateAlternateColorCodes('&', l)
                					.replace("%amount%", NumberFormatting.format(BountyAPI.getBounty(pz)))
                					.replace("%status%", ChatColor.translateAlternateColorCodes('&', "&c&lOFFLINE")));
                    	}
                    	meta.setLore(lore);
                    	skull.setItemMeta(meta);
                    	ALL.add(skull);
                    	
                    	for(currentSlot = 0; currentSlot < (rows * cols); currentSlot++) {
                			int index = page * (rows * cols) + currentSlot;
                			
                			if(index >= ALL.size()) {
                				lastPage = true;
                				break;
                			} else {
                				lastPage = false;
                				ItemStack currentItem = ALL.get(index);
                				inv.setItem(currentSlot, currentItem);
                			}
                		}
            		} else {
            			continue;
            		}
            	} else {
            		continue;
            	}
            } else {
            	if(p.canSee(z)) {
            		if(BountyAPI.getBounty(z) != 0) {
            			meta.setOwner(configL.get(x));
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getString("Inventory.Heads.Name")
                        		.replace("%player%", configL.get(x))));
                        
                        for (String l : lo) {
                        	if(z != null) {
                        		lore.add(ChatColor.translateAlternateColorCodes('&', l)
                            			.replace("%amount%", NumberFormatting.format(BountyAPI.getBounty(z)))
                            			.replace("%status%", ChatColor.translateAlternateColorCodes('&', "&a&lONLINE")));
                        	}
                        }
                        meta.setLore(lore);
                        skull.setItemMeta(meta);
                        ALL.add(skull);
                        
                        for(currentSlot = 0; currentSlot < (rows * cols); currentSlot++) {
                			int index = page * (rows * cols) + currentSlot;
                			
                			if(index >= ALL.size()) {
                				lastPage = true;
                				break;
                			} else {
                				lastPage = false;
                				ItemStack currentItem = ALL.get(index);
                				inv.setItem(currentSlot, currentItem);
                			}
                		}
            		} else {
            			continue;
            		}
            	} else {
            		if(plugin.getConfig().getBoolean("Inventory.Offline")) {
            			OfflinePlayer pz = plugin.getServer().getOfflinePlayer(configL.get(x));
            			
            			if(BountyAPI.getBounty(pz) != 0) {
            				meta.setOwner(pz.getName());
                        	meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getString("Inventory.Heads.Name")
                        			.replace("%player%", pz.getName())));
                        	
                        	for(String l : lo) {
                        		lore.add(ChatColor.translateAlternateColorCodes('&', l)
                    					.replace("%amount%", NumberFormatting.format(BountyAPI.getBounty(pz)))
                    					.replace("%status%", ChatColor.translateAlternateColorCodes('&', "&c&lOFFLINE")));
                        	}
                        	meta.setLore(lore);
                        	skull.setItemMeta(meta);
                        	ALL.add(skull);
                        	
                        	for(currentSlot = 0; currentSlot < (rows * cols); currentSlot++) {
                    			int index = page * (rows * cols) + currentSlot;
                    			
                    			if(index >= ALL.size()) {
                    				lastPage = true;
                    				break;
                    			} else {
                    				lastPage = false;
                    				ItemStack currentItem = ALL.get(index);
                    				inv.setItem(currentSlot, currentItem);
                    			}
                    		}
            			} else {
            				continue;
            			}
                	} else {
                		continue;
                	}
            	}
            }
		}
		if(!lastPage){inv.setItem(nextPage, next);} else {inv.setItem(nextPage, divi);}
		inv.setItem(52, divi);
		inv.setItem(51, divi);
		inv.setItem(50, divi);
		inv.setItem(49, divi);
		inv.setItem(48, divi);
		inv.setItem(47, divi);
		inv.setItem(46, divi);
		if(page > 0){inv.setItem(prevPage, prev);} else {inv.setItem(prevPage, divi);}
		
		myPage.put(p, 0);
		ALL.clear();
		
		return inv;			
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		ItemStack c = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		Inventory inv = p.getOpenInventory().getTopInventory();
		
		String invTitle = ChatColor.translateAlternateColorCodes('&', plugin.getString("Inventory.Name")
				.replace("%page%", ""));
		
		int slotID = e.getSlot();
		
		if(c == null) return;
		if(!c.hasItemMeta()) return;
		
		if(inv.getType() == InventoryType.PLAYER) {
			if(hasInvOpen.containsKey(p)) {
				if(hasInvOpen.get(p)) {
					e.setCancelled(true);
				}
			}
		}
		
		if(inv.getName().contains(invTitle)) {
			e.setCancelled(true);
			
			if(c.getType() == Material.PAPER) {
				int currentPage = myPage.get(p);
				if(slotID == prevPage) {
					Inventory page = InventoryItems(currentPage-1, p);
					p.openInventory(page);
					myPage.put(p, currentPage-1);
				}
				if(slotID == nextPage) {
					Inventory page = InventoryItems(currentPage+1, p);
					p.openInventory(page);
					myPage.put(p, currentPage+1);
				}
			}
		}
	}
	
	@EventHandler
	public void onInvOpen(InventoryOpenEvent e) {
		if(e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			String pageTitle = "&9Bounty Page: &5&l%page%";
			String invTitle = ChatColor.translateAlternateColorCodes('&', pageTitle
					.replace("%page%", ""));
			Inventory inv = e.getInventory();
			
			if(inv.getName().equals(invTitle)) {
				hasInvOpen.put(p, true);
			}
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if(e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			String pageTitle = "&9Bounty Page: &5&l%page%";
			String invTitle = ChatColor.translateAlternateColorCodes('&', pageTitle
					.replace("%page%", ""));
			Inventory inv = e.getInventory();
			
			if(inv.getName().equals(invTitle)) {
				hasInvOpen.put(p, false);
			}
		}
	}
}
