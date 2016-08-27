package com.aliumcraft.playerbounty;

import java.io.File;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.aliumcraft.playerbounty.cmds.BountyCmd;
import com.aliumcraft.playerbounty.listeners.ClaimListeners;
import com.aliumcraft.playerbounty.listeners.FileManagerListeners;
import com.aliumcraft.playerbounty.listeners.ParticleListeners;
import com.aliumcraft.playerbounty.listeners.StreakListeners;
import com.aliumcraft.playerbounty.utils.Messages;
import com.aliumcraft.playerbounty.ymls.ConfigYML;
import com.aliumcraft.playerbounty.ymls.MessagesYML;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private static Main instance;
	private File bountyF = new File(getDataFolder(), "bounty-data");
	private File messagesF = new File(getDataFolder(), "messages.yml");
	private FileConfiguration messagesConf;
	private File configF = new File(getDataFolder(), "config.yml");
	public static Economy econ = null;
	
	public void onEnable() {
		instance = this;
		
		if(!setupEconomy()) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		loadMyFiles();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new FileManagerListeners(), this);
		pm.registerEvents(new ClaimListeners(), this);
		pm.registerEvents(new ParticleListeners(), this);
		pm.registerEvents(new StreakListeners(), this);
		
		getCommand("bounty").setExecutor(new BountyCmd());
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
	
	public static Main getInstance() {
		return instance;
	}
	
	private void loadMyFiles() {
		if(!getDataFolder().exists()) getDataFolder().mkdirs();
		
		if(!bountyF.exists()) bountyF.mkdirs();
		if(!messagesF.exists()) MessagesYML.loadFile(messagesF);
		if(!configF.exists()) ConfigYML.loadConfig();
		
		messagesConf = YamlConfiguration.loadConfiguration(messagesF);
		
		Messages.setFile(messagesConf);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			loadPlayerFile(p);
		}
	}
	
	public void saveFile(File f, FileConfiguration c) {
		try {
			c.save(f);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reloadFiles() {
		messagesConf = YamlConfiguration.loadConfiguration(messagesF);
		reloadConfig();
	}
	
	public FileConfiguration getMessagesConfig() {
		return messagesConf;
	}
	
	public void saveMessagesConfig() {
		saveFile(messagesF, messagesConf);
	}
	
	public File getBountyFile() {
		return bountyF;
	}
	
	public File getBountyFile(Player p) {
		return new File(getBountyFile(), p.getUniqueId().toString() + ".yml");
	}
	
	public File getBountyFile(OfflinePlayer p) {
		return new File(getBountyFile(), p.getUniqueId().toString() + ".yml");
	}
	
	public void loadPlayerFile(Player p) {
		File f = getBountyFile(p);
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		
		if(f.exists()) {
			
			c.set("Name", p.getName());
		
		} else {
			
			c.set("Name", p.getName());
			c.set("Amount", 0);
			c.set("TotalBounty", 0);
			c.set("BountiesClaimed", 0);
			c.set("BountyStreak", 0);
			c.set("HasBounty", false);
		}
		
		saveFile(f, c);
	}
	
	public String format(double value) {
	    DecimalFormat df = new DecimalFormat("###,###,###,###,###,###,###.##");
	    return df.format(value);
	}
}
