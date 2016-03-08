package com.aliumcraft.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.aliumcraft.Main;

import net.md_5.bungee.api.ChatColor;

public class onClick implements Listener {
	Main plugin;
	
	public onClick(Main pl) {
		this.plugin = pl;
	}
	
	@EventHandler
	public void onClickEvent(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		ItemStack c = e.getCurrentItem();
		
		if(inv.getName().contains(ChatColor.translateAlternateColorCodes('&', "&e&lArena &f&l-"))) {
			e.setCancelled(true);
			if(c == null || c.getType() == Material.AIR) {
				return;
			}
		}
	}
}
