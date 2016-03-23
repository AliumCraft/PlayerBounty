package com.aliumcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BountyList implements Listener {
	Main plugin;
	public BountyList(Main inst) {
		this.plugin = inst;
	}
	
	private static List<ItemStack> ALL = new ArrayList<ItemStack>();
	final static int nextPage = 53;
	final static int prevPage = 45;
	
	static HashMap<Player, Integer> myPage = new HashMap<Player, Integer>();
	
	@SuppressWarnings("deprecation")
	public Inventory InventoryItems(int page, Player p) {
		int x;
		List<String> configL = plugin.getConfig().getStringList("BountyList");
		
		int pageTitle = page +1;
		
		final Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&9&lBounty Contracts Page: &5&l" + pageTitle));
		final ItemStack divi = Main.getItem(Material.THIN_GLASS, 1, 0, "&4", null);
		final ItemStack next = Main.getItem(Material.PAPER, 1, 0, "&e&lNext Page ->", null);
		final ItemStack prev = Main.getItem(Material.PAPER, 1, 0, "&e&l<- Previous Page", null);
		
		boolean lastPage = false;
		final int cols = 9;
		final int rows = 5;
		
		int currentSlot = 0;
		Integer size = configL.size();
		
		for(x=0; x < size; x++) {
			ItemStack skull = new ItemStack(397, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            List<String> lo = plugin.getStrings("Heads.Lore");
            List<String> lore = new ArrayList<String>();
            Player z = plugin.getServer().getPlayer(configL.get(x));
            
            if(z == null) {
            	continue;
            } else {
            	meta.setOwner(configL.get(x));
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getString("Heads.Name")
                		.replace("%player%", configL.get(x))));
                
                for (String l : lo) {
                	if(z != null) {
                		if(plugin.getConfig().contains("Bounties." + z.getUniqueId().toString())) {
                			lore.add(ChatColor.translateAlternateColorCodes('&', l)
                        			.replace("%amount%", String.valueOf(plugin.getConfig().getDouble("Bounties." + z.getUniqueId())))
                        			.replace("%status%", ChatColor.translateAlternateColorCodes('&', "&a&lONLINE")));
                		}
                	} else {
                		lore.add(ChatColor.translateAlternateColorCodes('&', l)
                    			.replace("%amount%", String.valueOf(plugin.getConfig().getDouble("Bounties." + configL.get(x))))
                    			.replace("%status%", ChatColor.translateAlternateColorCodes('&', "&c&lOFFLINE")));
                	}
                }
                meta.setLore(lore);
                skull.setItemMeta(meta);
                ALL.add(skull);
                
                for(currentSlot = 0; currentSlot < (rows * cols); currentSlot++) {
        			int index = page * (rows * cols) + currentSlot;
        			
        			System.out.println(ALL.size());
        			System.out.println(index);
        			
        			if(index >= ALL.size()) {
        				lastPage = true;
        				break;
        			} else {
        				lastPage = false;
        				ItemStack currentItem = ALL.get(index);
        				inv.setItem(currentSlot, currentItem);
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
	
	@SuppressWarnings("null")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		ItemStack c = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		
		String bounty = "Bounty Contracts Page: ";
		String currentName = inv.getName();
		
		int slotID = e.getSlot();
		
		if(c == null && c.getType() == Material.AIR) {
			return;
		}
		
		if(currentName.contains(bounty)) {
			int currentPage = myPage.get(p);
						
			e.setCancelled(true);
			
			if(c.getType() == Material.PAPER) {
				
				switch(slotID) {
				case nextPage:
					currentPage++;
					break;
				case prevPage:
					currentPage--;
					break;
				}
				
				if(currentPage >= 0) {
					myPage.put(p, currentPage);
					int testPage = myPage.get(p);
					
					Inventory page = InventoryItems(testPage, p);
					p.openInventory(page);
					return;
				}
			}
			
			
		}
	}
}
