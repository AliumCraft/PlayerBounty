package com.aliumcraft.events;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aliumcraft.Main;

public class SignEvent implements Listener {
	
	//These will define what line particular data exists on signs.
	// Figured I would use this as a central point of configuration
	final int SIGN_HEADING = 0;	
	final int SIGN_PLAYER = 1;
	final int SIGN_MAP = 2;
	final int SIGN_ACTION = 3;
	
	Main plugin;
	
	HashMap<Integer,Integer> signSlotsUsed = new HashMap<Integer,Integer>();
	
	public SignEvent(Main is) {
		this.plugin = is;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		
        Player p = e.getPlayer();
        BlockState thisSign = e.getBlock().getState();
        System.out.println(":-----------------------------------------------: SIGN EVENT!");
        thisSign.update();
        Sign objSign = (Sign)thisSign;
        System.out.println("?>" + objSign.getLine(2));
        
        if(!signIsValid(thisSign))return;
        
        if(signUpdateText(thisSign))
        {
        	p.sendMessage("Sign updated");      	
        }

    }
	
	public void afterSignCreation (BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		BlockState thisSign = e.getBlockPlaced().getState();
		System.out.println(":-----------------------------------------------: BLOCK EVENT!");
		thisSign.update(true);
        if(!signIsValid(thisSign))return;
        
        if(signUpdateText(thisSign))
        {
        	p.sendMessage("Sign updated");      	
        }	
		
	}
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		Block b = e.getClickedBlock();
		Player p = e.getPlayer();
		if(b != null)
		{
			
			BlockState s = b.getState();
			if((s instanceof Sign))
			{
				
				Sign thisSign = (Sign)s;
						
				
					if(signIsValid(thisSign))
					{
						//if((p.isSneaking()) && (p.hasPermission("paintball.admin")) &&
						//		(e.getAction() == Action.LEFT_CLICK_BLOCK)) {
						//	return;
						//}
						int hash = thisSign.hashCode();
				        if(signSlotsUsed.containsKey(hash))
				        {
				        	
				        }
				        else
				        {
				        	if(signCreateNewSign(thisSign))
				        	{
				        		
				        		//a new sign was created. create a blank configuration entry
				        		p.sendMessage("New world");
				        		configCreateEntry(thisSign);
				        		p.sendMessage("New world complete!");
				        	}
				        }
				        
						
						
						if(signAddsPlayersToRoom(s, 1))
						{
							p.sendMessage("Room is full! Emptying room");
							signEmptyPlayers(s);
						}
						else
						{
							p.sendMessage("Adding a player :D");
							signTeleportToMapLimbo(thisSign, p);
						}
						System.out.print("-------------------------------: CURRENT STATE UPDATE");
						signUpdateText(s);
						System.out.print("-------------------------------: CURRENT STATE PASS?");
						//if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							//Code here to add them to the arena.
						//}
					}
					else
					{
	
						
					}
			}

		}
				
	}

	public boolean signAddsPlayersToRoom(BlockState sign, int amount)//handles adding an extra player to the sign. Returns true if the room is full. //REQUIRES CHECKING IF PROPER SIGN FIRST
	{
		int thisSignHash = sign.hashCode();
		int currentCount = signSlotsUsed.get(thisSignHash);  //get the current amount of players
		int maxPlayers = (int)signGetMapSetting(sign,"Max-players");

		boolean roomFull = false;
		
		currentCount += amount; //add players. Or subtract if a negative number is passed 
		if(currentCount < 0)currentCount = 0;
		if(currentCount > maxPlayers)
		{
			currentCount = maxPlayers;
			roomFull = true;
		}
		
		signSlotsUsed.put(thisSignHash, currentCount); //update the value in hash
		
		return roomFull; // you can determine if by adding a player it fills the room to stop adding more.
		// I initially used:    return (currentCount == maxPlayers) 
		// but this didn't allow me to tell the difference between already full, and just been filled.
	
	}
	public Object signGetMapSetting(BlockState sign,String setting)
	{
		Object result;
		String mapName, mapPath;
		FileConfiguration config;
		config = plugin.getConfig();
		Sign thisSign = (Sign) sign;
		mapName = ChatColor.stripColor(thisSign.getLine(SIGN_MAP));
		//to get rid of the crap that isn't the maps
		
		
		mapPath = "Arena.map." + mapName + "." + setting;
		result = config.get(mapPath);
		
		System.out.print("MAP: " + mapName);
		System.out.print("PTH: " + mapPath);
		System.out.print("RES: " + result);
		
		return result;
	}
	public boolean signEmptyPlayers(BlockState sign)
	{
		Sign thisSign = (Sign)sign;
        if(signIsValid(thisSign))
        {
    		int thisSignHash = sign.hashCode();
    		signSlotsUsed.put(thisSignHash, 0);
    		return true; // sign has been emptied
        }
        return false; // not a sign

	}
	public boolean signUpdateText(BlockState sign)
	{
		Sign thisSign = (Sign)sign;
		//get line requirements
		int minPlayers = (int) signGetMapSetting(thisSign, "Min-players");
		int maxPlayers = (int) signGetMapSetting(thisSign, "Max-players");
		int currentPlayers = signSlotsUsed.get(thisSign.hashCode()); 
		String mapName = (String) signGetMapSetting(thisSign, "name");
		
		System.out.print("---------------------------: MAP        : " + mapName);
		System.out.print("---------------------------: CUR PLAYERS: " + currentPlayers);
		System.out.print("---------------------------: MIN PLAYERS: " + minPlayers);
		System.out.print("---------------------------: MAX PLAYERS: " + maxPlayers);
		
		
		
		
		
		if(signIsValid(thisSign))
        {
			String configLine;
			for(int i = 0; i <= 3; i++ )
			{
				System.out.print("---------------------------------- CONFIGLINE EVOLUTION:");
				configLine = plugin.getConfig().getString("Signs.Line-" + (i+1));
				System.out.print("----------------------------------                     : " + configLine);
				configLine = configLine.replace("%amount%", "" + currentPlayers);
				System.out.print("----------------------------------                     : " + configLine);
				configLine = configLine.replace("%min%", "" + minPlayers);
				System.out.print("----------------------------------                     : " + configLine);
				configLine = configLine.replace("%max%", "" + maxPlayers);
				System.out.print("----------------------------------                     : " + configLine);
				configLine = configLine.replace("%map%", "" + mapName);
				System.out.print("----------------------------------                     : " + configLine);
				thisSign.setLine(i, ChatColor.translateAlternateColorCodes('&', configLine));
			}
       
        	String line1 = ChatColor.stripColor(thisSign.getLine(0));
        	String line2 = ChatColor.stripColor(thisSign.getLine(1));
        	String line3 = ChatColor.stripColor(thisSign.getLine(2));
        	String line4 = ChatColor.stripColor(thisSign.getLine(3));
        	
        	
        	System.out.println("|-----------------V");
        	System.out.println("| " + line1);
        	System.out.println("| " + line2);
        	System.out.println("| " + line3);
        	System.out.println("| " + line4);
        	System.out.println("|-----------------^");
        	thisSign.update();
        	return true; //update occured
        }        
        return false; //not a proper sign
	}
	public boolean signCreateNewSign(BlockState sign)
	{
		int thisSign = sign.hashCode();
		
		if(signSlotsUsed.containsKey(thisSign))
		{
			return false;
		}
		else
		{
			signSlotsUsed.put(thisSign, 0);
			return true;
		}
	}
	public boolean signIsValid(BlockState sign)
	{
	
		Sign thisSign = (Sign)sign;
		
		String configLine1 = (plugin.getConfig().getString("Signs.Line-1")).replaceAll("&[A-z0-9]","").toLowerCase();
		String topLine = ChatColor.stripColor(thisSign.getLine(SIGN_HEADING)).toLowerCase();
		
		System.out.print(configLine1 + " <---> " + topLine);
		
        if(topLine.toLowerCase().contains(configLine1))
        {
        	System.out.println("-----------------------------: Paintball sign clicked");
        	return true;
        }
        	
        return false;
    }
	public void signTeleportToMapLimbo(BlockState sign, Player player)//This will teleport the player to the Limbo waiting room for that map
	{
		Sign thisSign = (Sign) sign;
		double limboX;
		double limboY;
		double limboZ;
		Location playerLocation = player.getLocation();
		
		String mapName;
		FileConfiguration config;
		config = plugin.getConfig();
		
		mapName = ChatColor.stripColor(thisSign.getLine(SIGN_MAP));
		
		limboX = config.getDouble("Arena.map." + mapName + ".Limbo.x");
		limboY = config.getDouble("Arena.map." + mapName + ".Limbo.y");
		limboZ = config.getDouble("Arena.map." + mapName + ".Limbo.z");
		
		playerLocation.setX(limboX);
		playerLocation.setY(limboY);
		playerLocation.setZ(limboZ);
		
		player.teleport(playerLocation);
		
		System.out.println("Player Teleported to (" + limboX + " | " + limboY + " | " + limboZ + ")");
		
	}
	public void configCreateEntry(BlockState sign)
	{
		FileConfiguration config = plugin.getConfig();
		Sign thisSign = (Sign)sign;
		
		if (signIsValid(thisSign))
		{		
			
			String signHeading = ChatColor.stripColor(thisSign.getLine(SIGN_HEADING));
			String signPlayers  = ChatColor.stripColor(thisSign.getLine(SIGN_PLAYER)); //   2/16     split by the / to get min and max
			String signMap = ChatColor.stripColor(thisSign.getLine(SIGN_MAP)); //If there is something here then save to config
			String signAction = ChatColor.stripColor(thisSign.getLine(SIGN_ACTION));
			
			String minPlayers = "";
			String maxPlayers = "";		
			if (signPlayers .contains("/"))
			{
				String[] players = signPlayers .split("/");
				minPlayers = players[0];
				maxPlayers = players[1];	
			}
			String pathMap = "Arena.map";
			String ThisMap = pathMap + "." + signMap;
			if(config.contains(ThisMap))
			{
				//map already exists
			}
			else
			{		
				//config = configAddToList(config, pathMap, signMap);
				config.set(ThisMap + ".name", signMap);
				config.set(ThisMap + ".Min-players", Integer.parseInt(minPlayers));
				config.set(ThisMap + ".Max-players", Integer.parseInt(maxPlayers));
				config.set(ThisMap + ".Limbo.x", 0);
				config.set(ThisMap + ".Limbo.y", 0);
				config.set(ThisMap + ".Limbo.z", 0);
				config.set(ThisMap + ".Team.Team-name", "Alpha");
				config.set(ThisMap + ".Team.spawn.location.x", 1);
				config.set(ThisMap + ".Team.spawn.location.y", 1);
				config.set(ThisMap + ".Team.spawn.location.z", 1);
			}
			
				System.out.print("Created entry for map: " + signMap);
				plugin.saveConfig();
			
		}
		
		
		
	}

}
