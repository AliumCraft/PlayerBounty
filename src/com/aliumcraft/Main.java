package com.aliumcraft;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
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
import com.aliumcraft.events.onClick;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	PrintStream p = System.out;
	public Config config = new Config(this);
	
	public void onEnable() {
		//Get a reference to the server, whichever way you can
		Server server = Bukkit.getServer();
		 
		ConsoleCommandSender console = server.getConsoleSender();
		
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Loading Paintball plugin..."));
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bMade by: &6AliumCraft"));
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new AntiDamage(this), this);
		pm.registerEvents(new SignEvent(this), this);
		pm.registerEvents(new Commands(this), this);
		pm.registerEvents(new firePaintball(this), this);
		pm.registerEvents(new Config(this), this);
		pm.registerEvents(new onClick(this), this);
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bClasses &7loaded."));

		config.loadMyConfig();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bConfig &7loaded."));
		
		getCommand("paintball").setExecutor(new Commands(this));
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCommand(s) &7loaded."));
		
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Paintball plugin is now &6loaded."));
	}

	public String getS(String string) {
		string.replace('&', '§');
		return getConfig().getString(string);
	}
	
	public void msg(Player p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	public void saveLoc(Location loc, String c) {
	    String location = loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
	    List<String> config = getConfig().getStringList("Arenas." + c);
	    
	    config.add(location);
	    getConfig().set("Arenas." + c, config);
	    saveConfig();
	}
   
    public Location getLocation(ConfigurationSection section, String path) {
        if (section.contains(path) && section.isString(path)) {
            try {
                String[] location = section.getString(path).split(",");
                if (location.length == 6 && Bukkit.getWorld(location[0]) != null) {
                    return new Location(
                            Bukkit.getWorld(location[0]),
                            Double.parseDouble(location[1]),
                            Double.parseDouble(location[2]),
                            Double.parseDouble(location[3]),
                            Float.parseFloat(location[4]),
                            Float.parseFloat(location[5])
                        );
                }
            } catch (Exception error) {
               
            }
        }
        return null;
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		//Lobby teleport on join.
		
		Player p = (Player)e.getPlayer();
		
		double x = getConfig().getDouble("Lobby.X");
		double y = getConfig().getDouble("Lobby.Y");
		double z = getConfig().getDouble("Lobby.Z");
		
		p.teleport(new Location(p.getWorld(), x, y, z));
	}
	
	public static ItemStack getItem(Material material, int itemAmount, int itemData, String name, List<String> lores) {
		ItemStack item = new ItemStack(material, itemAmount, (byte) itemData);
		if (name != null) {
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			item.setItemMeta(meta);
		}
		if (lores != null) {
			List<String> lore = new ArrayList<String>();
			for (String l : lores) {
				lore.add(ChatColor.translateAlternateColorCodes('&', l));
			}
			ItemMeta meta = item.getItemMeta();
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return item;
	}
}
