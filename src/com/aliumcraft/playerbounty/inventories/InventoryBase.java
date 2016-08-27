package com.aliumcraft.playerbounty.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryBase {

	@SuppressWarnings("deprecation")
	public ItemStack getItemStackFromFile(FileConfiguration file, String path) {
		String item = file.getString(path + "Item");
		int id = 0;
		int data = 0;
		
		if(item.contains(":")) {
			String[] a = item.split(":");
			
			id = Integer.valueOf(a[0]);
			data = Integer.valueOf(a[1]);
		} else {
			id = Integer.valueOf(item);
		}
		
		ItemStack is = new ItemStack(Material.getMaterial(id), 1, (short) data);
		String name = file.getString(path + "Name");
		List<String> lore = file.getStringList(path + "Lore");
		ItemMeta im = is.getItemMeta();
		List<String> array = new ArrayList<String>();
		
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		
		for(String s : lore) {
			array.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		
		im.setLore(array);
		is.setItemMeta(im);
		
		return is;
	}
}
