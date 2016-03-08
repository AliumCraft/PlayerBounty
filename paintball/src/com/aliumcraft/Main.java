package com.aliumcraft;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.aliumcraft.events.AntiDamage;
import com.aliumcraft.events.SignEvent;
import com.aliumcraft.events.firePaintball;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	PrintStream p = System.out;
	
	public void onEnable() {
		p.println("Loading Paintball plugin...");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new AntiDamage(this), this);
		pm.registerEvents(new SignEvent(this), this);
		pm.registerEvents(new firePaintball(), this);
		p.println("classes loaded.");

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		p.println("config loaded.");
		
		
		
	
		
		
		System.out.println("Paintball plugin loaded...");
	}

	public String getS(String string) {
		return getConfig().getString(string);
	}
	
	public void msg(Player p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	public void giveAmmoToPlayer(Player p, ItemStack item)
	{
		List<String> color = new ArrayList<String>();
		color.add("Ammo for paintball");
		
		ItemStack redPaint = item;
		ItemMeta text = redPaint.getItemMeta();
		text.setDisplayName("Ammo");
		text.setLore(color);
		

		redPaint.setItemMeta(text);
				
		
		p.getInventory().addItem(redPaint);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		//Lobby teleport on join.
		
		Player p = (Player)e.getPlayer();
		
		double x = getConfig().getDouble("Lobby.X");
		double y = getConfig().getDouble("Lobby.Y");
		double z = getConfig().getDouble("Lobby.Z");
		
		p.teleport(new Location(p.getWorld(), x, y, z));
		giveAmmoToPlayer(p, new ItemStack(Material.BAKED_POTATO)); //LOL throwing baked potatoes!
		
		//make ammo
		

		
	}
}
