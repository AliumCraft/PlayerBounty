package com.aliumcraft.playerbounty.inventories;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.utils.Messages;

public class BountyListInv extends InventoryBase {
	
	public BountyListInv(Player p) {
		this.p = p;
		this.name = plugin.getConfig().getString("BountyGUI.Name");
		
		fillHashMaps();
	}
	
	private final Player p;
	private Main plugin = Main.getInstance();
	private String name;
	private Map<Integer,Inventory> mapOfPages = new HashMap<Integer,Inventory>();
	private Map<OfflinePlayer,Double> playersWithBounties = new HashMap<OfflinePlayer,Double>();
	private final int prevPage = 45;
	private final int nextPage = 53;
	private final int[] dividers = {45,46,47,48,49,50,51,52,53};
	private int amountOfBounties;
	private boolean isThereAnyBounties = false;
	
	private void fillHashMaps() {
		File dir = plugin.getBountyFile();
		File[] dirContent = dir.listFiles();
		
		for(File child : dirContent) {
			FileConfiguration c = YamlConfiguration.loadConfiguration(child);
			
			if(!c.contains("HasBounty")) continue;
			if(!c.getBoolean("HasBounty")) continue;
			String name = child.getName().replace(".yml", "");
			UUID uuid = UUID.fromString(name);
			double amount = c.getDouble("Amount");
			
			playersWithBounties.put(Bukkit.getOfflinePlayer(uuid), amount);
		}
		
		getAmountOfBountiesInHashMap();
	}
	
	private void getAmountOfBountiesInHashMap() {
		int a = playersWithBounties.size();
		
		amountOfBounties = a;
		
		createBountyListInventory();
	}
	
	private void createBountyListInventory() {
		ItemStack divider = getItemStackFromFile(plugin.getConfig(), "BountyGUI.Divider.");
		ItemStack nPage = getItemStackFromFile(plugin.getConfig(), "BountyGUI.NextPage.");
		ItemStack pPage = getItemStackFromFile(plugin.getConfig(), "BountyGUI.PrevPage.");
		double pages = amountOfBounties / 45;
		if(pages == 0.0D) pages = 1.0D;
		int amountOfPages = 0;
		
		amountOfPages = (int) Math.ceil(pages);
		int i = 0;
		
		do {
			i++;
			
			mapOfPages.put(i, Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', name.replace("{page}", ""+i))));
			Inventory inv = mapOfPages.get(i);
			
			for(int d : dividers) {
				inv.setItem(d, divider);
			}
			
			if(i != 1) inv.setItem(prevPage, pPage);
			if(i != amountOfPages) inv.setItem(nextPage, nPage);
			
		} while(i <= (amountOfPages - 1));
		
		populateInventoryWithHeads();
	}
	
	private void populateInventoryWithHeads() {
		int num = 0;
		ItemStack headBase = getItemStackFromFile(plugin.getConfig(), "BountyGUI.Heads.");
		
		for(OfflinePlayer p : playersWithBounties.keySet()) {
			if(p.isOnline()) {
				Player op = Bukkit.getPlayer(p.getUniqueId());
				
				if(!this.p.canSee(op)) continue;
			}
			
			num++;
			
			int i = num / 45;
			if(i == 0) i = 1;
			
			ItemStack playerItemStack = getPlayerHead(p, headBase);
			mapOfPages.get(i).addItem(playerItemStack);
		}
		
		if(!playersWithBounties.isEmpty()) isThereAnyBounties = true;
	}
	
	private ItemStack getPlayerHead(OfflinePlayer p, ItemStack base) {
		ItemStack is = base.clone();
		SkullMeta im = (SkullMeta) is.getItemMeta();
		String name = im.getDisplayName();
		String playerName = p.getName();
		double bountyAmount = playersWithBounties.get(p);
		List<String> currentLore = im.getLore();
		List<String> newLore = new ArrayList<String>();
		Player onlineStatusPlayer = Bukkit.getPlayer(p.getUniqueId());
		boolean onlineStatus = (onlineStatusPlayer == null) ? false : true;
		
		im.setOwner(playerName);
		im.setDisplayName(name.replace("{p}", playerName));
		
		for(String s : currentLore) {
			if(s.contains("{amount}")) s = s.replace("{amount}", plugin.format(bountyAmount));
			if(s.contains("{status}") && onlineStatus) s = s.replace("{status}", ChatColor.translateAlternateColorCodes('&', "&a&lONLINE"));
			if(s.contains("{status}") && !onlineStatus) s = s.replace("{status}", ChatColor.translateAlternateColorCodes('&', "&c&lOFFLINE"));
			
			newLore.add(s);
		}
		
		im.setLore(newLore);
		is.setItemMeta(im);
		
		return is;
	}
	
	
	public void openInventory(int page) {		
		if(!isThereAnyBounties) {
			Messages.BountyList_NoBounties.msg(p);
			return;
		}
		
		p.openInventory(mapOfPages.get(page));
		plugin.currentlyOpenPage.put(p, page);
	}
	
	public int getPage() {
		return plugin.currentlyOpenPage.get(p);
	}
}
