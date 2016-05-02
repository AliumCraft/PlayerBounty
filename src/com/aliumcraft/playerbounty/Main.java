package com.aliumcraft.playerbounty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.aliumcraft.playerbounty.util.BountyClaim;
import com.aliumcraft.playerbounty.util.BountyList;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;

	public Config config = new Config(this);
	public Commands cmd = new Commands(this);
	public BountyList bl = new BountyList(this);
	public BountyClaim bc = new BountyClaim(this);
	public Events pe = new Events(this, bl);
	
	public static Main instance;
	
	private final String DB_HOST = getConfig().getString("Database.HOST");
	private final String DB_PORT = getConfig().getString("Database.PORT");
	private final String DB_NAME = getConfig().getString("Database.NAME");
	public static String DB_TABLE;
	private final String DB_USER = getConfig().getString("Database.USER");
	private final String DB_PASS = getConfig().getString("Database.PASS");
	private final String database = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
	
	public static Connection conn;
	public static Statement s;
	
	File messagesFile;
	static File bountyFile;
	public FileConfiguration messages;
	public static FileConfiguration bounty;
	
	public void onEnable() {
		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		instance = this;
		
		log.info(String.format("[%s] Checking server version...", getDescription().getName()));
		if(Bukkit.getBukkitVersion().startsWith("1.7")) {
			log.info(String.format("[%s] Loading plugin with Spigot 1.7 build.", getDescription().getName()));
		} else if (Bukkit.getBukkitVersion().startsWith("1.8")) {
			log.info(String.format("[%s] Loading plugin with Spigot 1.8 build.", getDescription().getName()));
		} else if (Bukkit.getBukkitVersion().startsWith("1.9")) {
			log.info(String.format("[%s] Loading plugin with Spigot 1.9 build.", getDescription().getName()));
		}
		
		if(getDataFolder().exists()) {
			DB_TABLE = getConfig().getString("Database.TABLE");
			if(getConfig().getBoolean("Database.Enabled")) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(database, DB_USER, DB_PASS);
					
					log.info(String.format("[%s] Database successfully connected.", getDescription().getName()));
					
					s = conn.createStatement();
					
					try {
						String createTable = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + " (UUID varchar(32) PRIMARY KEY, name varchar(16), bounty double, bountiesClaimed int, TotalBounty double)";
						s.execute(createTable);
						getLogger().info("Table " + DB_TABLE + " successfully created.");
					} catch(Exception e) {
						e.printStackTrace();
					}
					
					try {
						String sql = "INSERT INTO " + DB_TABLE + " (uuid, name, bounty, bountiesClaimed, TotalBounty) VALUES ('4dbb2e5f20b94aac8b9c0cbc785f780c', 'ThroatGrinder', 0, 0, 0)";
						s.executeUpdate(sql);
						getLogger().info("Successfully created a row.");
					} catch (Exception e) {
						getLogger().info("Row already exists.");
					}
					
					getLogger().info("Database successfully loaded.");
				} catch(Exception ex) { 
					ex.printStackTrace();
				}
			}
		}
		
		messagesFile = new File(getDataFolder(), "messages.yml");
		bountyFile = new File(getDataFolder(), "bounty.yml");
		
		try {
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		messages = new YamlConfiguration();
		bounty = new YamlConfiguration();
		loadYamls();

		PluginManager pm = getServer().getPluginManager();
		config.loadMyConfig();
		pm.registerEvents(bl, this);
		pm.registerEvents(bc, this);
		pm.registerEvents(pe, this);

		getCommand("bounty").setExecutor(cmd);
		log.info(String.format("[%s]", getDescription().getName()));
		log.info(String.format("[%s] PlayerBounty (Java 8) successfully loaded.", getDescription().getName()));
		
		if(getConfig().getBoolean("Database.Enabled")) {
			new BukkitRunnable() {
				public void run() {
					String topBounty = "SELECT MAX( bounty ) FROM " + DB_TABLE;
					try {
						ResultSet rS = s.executeQuery(topBounty);
						rS.next();
						String amount = rS.getString(1);
						
						System.out.println("TOP BOUNTY: " + amount);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}.runTaskTimer(this, 72000L, 72000L);
		}
	}
	
	public void onDisable() {
		saveYamls();
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public void saveBounty() {
		try {
			bounty.save(bountyFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveBountyStatic() {
		try {
			bounty.save(bountyFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void firstRun() throws Exception {
        if(!messagesFile.exists()){                        
        	messagesFile.getParentFile().mkdirs();        
            copy(getResource("messages.yml"), messagesFile);
        }
        if(!bountyFile.exists()) {
        	bountyFile.getParentFile().mkdirs();
        	copy(getResource("bounty.yml"), bountyFile);
        }
    }
	
	private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
	
	public void saveYamls() {
	    try {
	        messages.save(messagesFile);
	        bounty.save(bountyFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void loadYamls() {
	    try {
	        messages.load(messagesFile);
	        bounty.load(bountyFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
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
	
	public String getMessage(String message) {
		message.replace('&', '§');
		return messages.getString(message);
	}
	
	public List<String> getMessages(String message) {
		message.replace('&', '§');
		return messages.getStringList(message);
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
}
