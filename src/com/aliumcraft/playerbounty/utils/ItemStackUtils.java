package com.aliumcraft.playerbounty.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class ItemStackUtils {

	public ItemStack customItem(int type, int amount, byte data, String name, List<String> lore) {
		ItemStack is = new ItemStack(Material.getMaterial(type), amount, data);
		ItemMeta im = is.getItemMeta();
		
		if(name != "") {
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		}
		
		List<String> list = new ArrayList<String>();
		
		for(String s : lore) {
			list.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		
		im.setLore(list);
		is.setItemMeta(im);
		
		return is;
	}
}
