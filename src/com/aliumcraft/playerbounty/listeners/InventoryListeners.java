package com.aliumcraft.playerbounty.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.aliumcraft.playerbounty.inventories.BountyListInv;
import com.aliumcraft.playerbounty.utils.ListenerBase;

public class InventoryListeners extends ListenerBase {

	private List<Player> hasInvOpen = new ArrayList<Player>();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		ItemStack c = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		String name = inv.getName();
		int slotId = e.getSlot();
		BountyListInv bli = new BountyListInv(p);
		String invTitle = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BountyGUI.Name").replace("{page}", ""));
		
		if(c == null) return;
		if(!c.hasItemMeta()) return;
		
		if((inv.getType() == InventoryType.PLAYER) && hasInvOpen.contains(p)) {
			e.setCancelled(true);
		}
		
		if(name.contains(invTitle)) {
			e.setCancelled(true);
			
			int currentPage = bli.getPage();
			
			if(c.getType().equals(Material.PAPER)) {
				if(slotId == 45) {
					bli.openInventory((currentPage + 1));
				}
				
				if(slotId == 53) {
					bli.openInventory((currentPage - 1));
				}
			}
		}
	}
	
	@EventHandler
	public void onInvOpen(InventoryOpenEvent e) {
		String invTitle = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BountyGUI.Name").replace("{page}", ""));
		
		if((e.getPlayer() instanceof Player)) {
			Player p = (Player) e.getPlayer();
			Inventory inv = e.getInventory();
			
			if(inv.getName().contains(invTitle)) {
				if(hasInvOpen.contains(p)) return;
				hasInvOpen.add(p);
			}
			
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		String invTitle = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BountyGUI.Name").replace("{page}", ""));
		
		if((e.getPlayer() instanceof Player)) {
			Player p = (Player) e.getPlayer();
			Inventory inv = e.getInventory();
			
			if(inv.getName().contains(invTitle)) {
				if(!hasInvOpen.contains(p)) return;
				hasInvOpen.remove(p);
			}
			
		}
	}
}
