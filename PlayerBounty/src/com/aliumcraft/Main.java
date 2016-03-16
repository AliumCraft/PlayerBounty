package com.aliumcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.aliumcraft.YMLs.Config;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin implements Listener {
	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;
	public static Permission perms = null;
	public static Chat chat = null;

	public Config config = new Config(this);
	public Commands cmd = new Commands(this);
	public BountyList bl = new BountyList(this);
	
	public void onEnable() {
		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(cmd, this);
		pm.registerEvents(config, this);
		config.loadMyConfig();
		pm.registerEvents(bl, this);

		getCommand("bounty").setExecutor(cmd);		
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public void msg(Player p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	public void bct(String msg) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	public String getString(String string) {
		string.replace('&', '§');
		return getConfig().getString(string);
	}
	
	public List<String> getStrings(String string) {
		string.replace('&', '§');
		return getConfig().getStringList(string);
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
	
	@EventHandler
	public void onKillEvent(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		Player killer = e.getEntity().getKiller();
		e.getEntity().getWorld();
		
		if(getConfig().contains("Bounties." + killed.getName())) {
			bountyClaim(killer, killed);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void bountyClaim(Player killer, Player killed) {
		String amount = String.valueOf(getConfig().getDouble("Bounties." + killed.getName()));
		Double amountD = getConfig().getDouble("Bounties." + killed.getName());
		List<String> bountyList = getConfig().getStringList("BountyList");
		
		if(getConfig().getDouble("Bounties." + killed.getName()) >=
				getConfig().getDouble("Bounty.Min-Bounty-Kill-Broadcast")) {
			for(String s : getStrings("Messages.Bounty-Claim")) {
				bct(ChatColor.translateAlternateColorCodes('&', s)
						.replace("%amount%", amount)
						.replace("%player%", killer.getName())
						.replace("%bounty%", killed.getName()));
			}
			msg(killer, getString("Messages.Money-Given")
					.replace("%amount%", amount));
		} else {
			msg(killer, getString("Messages.Money-Given")
					.replace("%amount%", amount));
		}
		econ.depositPlayer(killer.getName(), amountD);
		
		getConfig().set("Bounties." + killed.getName(), null);
		bountyList.remove(killed.getName());
		getConfig().set("BountyList", bountyList);
		saveConfig();
	}
}
