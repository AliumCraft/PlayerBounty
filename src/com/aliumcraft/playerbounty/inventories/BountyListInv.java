package com.aliumcraft.playerbounty.inventories;

import java.io.File;
import java.util.HashMap;
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

import com.aliumcraft.playerbounty.Main;

public class BountyListInv extends InventoryBase {
	
	private static Main plugin = Main.getInstance();
	private static String name = plugin.getConfig().getString("BountyGUI.Name");
	private Map<Integer,Inventory> mapOfPages = new HashMap<Integer,Inventory>();
	private Map<OfflinePlayer,Double> offlineBounties = new HashMap<OfflinePlayer,Double>();
	private Map<Player,Double> onlineBounties = new HashMap<Player,Double>();
	private final int prevPage = 45;
	private final int nextPage = 53;
	private final int[] dividers = {45,46,47,48,49,50,51,52,53};
	private int amountOfBounties;
	public boolean isThereAnyBounties = false;
	
	public void InitialCall() {
		ItemStack divider = getItemStackFromFile(plugin.getConfig(), "BountyGUI.Divider");
		ItemStack nPage = getItemStackFromFile(plugin.getConfig(), "BountyGUI.NextPage");
		ItemStack pPage = getItemStackFromFile(plugin.getConfig(), "BountyGUI.PrevPage");
		ItemStack headBase = getItemStackFromFile(plugin.getConfig(), "BountyGUI.Heads");
		int amountOfPages = 0;
		
		populateHashMaps();
		getAmountOfBounties();
		
		double pages = amountOfBounties / 45;
		amountOfPages = (int) Math.ceil(pages);
		int i = 0;
		
		do {
			i++;
			
			mapOfPages.put(i, Bukkit.createInventory(null, 54, name.replace("{page}", ""+i)));
			Inventory inv = mapOfPages.get(i);
			
			for(int d : dividers) {
				inv.setItem(d, divider);
			}
			
			if(i != 1) inv.setItem(prevPage, pPage);
			if(i != amountOfPages) inv.setItem(nextPage, nPage);
			
		} while(i <= (amountOfPages - 1));
		
		if((!offlineBounties.isEmpty()) || (!onlineBounties.isEmpty())) {
			isThereAnyBounties = true;
		}
	}
	
	private void populateHashMaps() {
		File dir = plugin.getBountyFile();
		File[] dirContent = dir.listFiles();
		
		for(File child : dirContent) {
			FileConfiguration c = YamlConfiguration.loadConfiguration(child);
			
			if(!c.contains("HasBounty")) continue;
			if(!c.getBoolean("HasBounty")) continue;
			UUID uuid = UUID.fromString(child.getName());
			Player p = Bukkit.getPlayer(uuid);
			double amount = c.getDouble("Amount");
			boolean isOnline = (p != null) ? true : false;
			
			if(isOnline) onlineBounties.put(p, amount);
			else offlineBounties.put(Bukkit.getOfflinePlayer(uuid), amount);
		}
	}
	
	private void getAmountOfBounties() {
		int a = onlineBounties.size();
		int b = offlineBounties.size();
		
		amountOfBounties = (a + b);
	}
	
	
	public static void openInventory(Player p) {
		//p.openInventory(inv);
	}
	
}
