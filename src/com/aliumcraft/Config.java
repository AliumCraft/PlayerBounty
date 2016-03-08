package com.aliumcraft;

import java.util.Arrays;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class Config implements Listener {
	Main plugin;

	public Config(Main main) {
		this.plugin = main;
	}
	
	public void loadMyConfig() {
		FileConfiguration c = this.plugin.getConfig();
		c.options().header("Paintball v" + this.plugin.getDescription().getVersion() + " Config File"
				+ "\ncreated by AliumCraft Dev Team."
				+ "\n"
				+ "\nMessages: Customize all the messages in the plugin."
				+ "\nArena-Defaults: These will be the defaults copied over when you create a new arena."
				+ "\nSigns: Set the lines of each sign."
				+ "\nDamage: Disable/Enable certain damages. True = disabled. False = enabled."
				+ "\nLobby: Where the player spawns when they join the game."
				+ "\nInventories: Set the customizations for each inventory in this plugin."
				+ "\n"
				);
				
		c.addDefault("Messages.Sign-created", "&b&lPaintball &6&l>> &eSign created!");
		c.addDefault("Messages.Arena-created", "&b&lPaintball &6&l>> &eArena &6%arena%&e created!");
		c.addDefault("Messages.Min-Set", "&b&lPaintball &6&l>> &eThe minimum players has been set to &6%amount% &efor the arena &6%arena%&e!");
		c.addDefault("Messages.Max-Set", "&b&lPaintball &6&l>> &eThe maximum players has been set to &6%amount% &efor the arena &6%arena%&e!");
		c.addDefault("Messages.No-permission", "&b&lPaintball &6&l>> &cYou do not have access to this command!");
		c.addDefault("Messages.Arena-exists", "&b&lPaintball &6&l>> &cThis arena already exists!");
		c.addDefault("Messages.Arena-NotExist", "&b&lPaintball &6&l>> &cThe arena &4%arena% &cdoes not exist!");
		c.addDefault("Messages.Invalid-arguments", "&b&lPaintball &6&l>> &cInvalid args! Please use /paintball for help!");
		c.addDefault("Messages.Too-Many-Spawnpoints", "&b&lPaintball &6&l>> &cThere is too many spawnpoints for this team! To add more increase the max players for this arena.");
		c.addDefault("Messages.Spawnpoint-Set", "&b&lPaintball &6&l>> &eA spawnpoint for &6%team% &eteam has been set for the &6%arena%&e arena.");
		c.addDefault("Messages.Invalid-Team", "&b&lPaintball &6&l>> &cThat is not a valid team!");
		
		c.addDefault("Arena-Defaults.min-players", Integer.valueOf(2));
		c.addDefault("Arena-Defaults.max-players", Integer.valueOf(16));
		c.addDefault("Arena-Defaults.Lobby.X", Double.valueOf(50));
		c.addDefault("Arena-Defaults.Lobby.Y", Double.valueOf(50));
		c.addDefault("Arena-Defaults.Lobby.Z", Double.valueOf(50));
		c.addDefault("Arena-Defaults.Lobby-Timer", Integer.valueOf(2));
		c.addDefault("Arena-Defaults.Red", Arrays.asList("world;0;0;0;0;0"));
		c.addDefault("Arena-Defaults.Blue", Arrays.asList("world;0;0;0;0;0"));
		
		c.addDefault("Signs.Lobby.Line-1", "[&1Paintball&0]");
		c.addDefault("Signs.Lobby.Line-2", "%amount%");
		c.addDefault("Signs.Lobby.Line-3", "%map%");
		c.addDefault("Signs.Lobby.Line-4", "&0Click to join.");
		c.addDefault("Signs.Leave.Line-1", "[&1Paintball&0]");
		c.addDefault("Signs.Leave.Line-2", "");
		c.addDefault("Signs.Leave.Line-3", "Click to return");
		c.addDefault("Signs.Leave.Line-4", "to lobby.");
		
		c.addDefault("Damage.Fall", Boolean.valueOf(true));
		c.addDefault("Damage.Drown", Boolean.valueOf(true));
		c.addDefault("Damage.Suffocation", Boolean.valueOf(true));
		c.addDefault("Damage.Contact", Boolean.valueOf(true));
		
		c.addDefault("Lobby.X", Double.valueOf(0));
		c.addDefault("Lobby.Y", Double.valueOf(63));
		c.addDefault("Lobby.Z", Double.valueOf(0));
		
		c.addDefault("Inventories.View.Name", "&e&lArena &f&l- &6%arena%");
		
		
		c.options().copyDefaults(true);
		this.plugin.saveConfig();
		this.plugin.reloadConfig();
	}
}
