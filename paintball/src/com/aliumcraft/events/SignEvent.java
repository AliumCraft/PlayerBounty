package com.aliumcraft.events;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aliumcraft.Main;

public class SignEvent implements Listener {
	Main plugin;
	
	HashMap<Integer,Integer> signSlotsUsed = new HashMap<Integer,Integer>();
	
	
	
	public SignEvent(Main is) {
		this.plugin = is;
	}
	
		
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        BlockState thisSign = e.getBlock().getState();

        
        
        if(signUpdateText(thisSign))
        {
        	p.sendMessage("Sign updated");
        }
       
        
    }
	
	/*
	public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        if(e.getLine(0).contains("[Paintball]")){
        	e.setLine(0, ChatColor.translateAlternateColorCodes('&', plugin.getS("Signs.Lobby.Line-1")));
        	e.setLine(1, ChatColor.translateAlternateColorCodes('&', plugin.getS("Signs.Lobby.Line-2"))
        			.replace("%amount%", "0/" + plugin.getConfig().getString("Arena.max-players")));
        	e.setLine(2, ChatColor.translateAlternateColorCodes('&', plugin.getS("Signs.Lobby.Line-3")));
        	e.setLine(3, ChatColor.translateAlternateColorCodes('&', plugin.getS("Signs.Lobby.Line-4")));
        	plugin.msg(p, plugin.getConfig().getString("Messages.Sign-created"));
        }
    } 
	
	 */
	
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
				String line1 = ChatColor.stripColor(thisSign.getLine(0));
				
				
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
				        	signCreateNewSign(thisSign);
				        }
				        
						
						
						if(signAddsPlayersToRoom(s, 1))
						{
							p.sendMessage("Room is full! Emptying room");
							signEmptyPlayers(s);
						}
						else
						{
							p.sendMessage("Adding a player :D");
						}
	
						signUpdateText(s);
						//if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							//Code here to add them to the arena.
						//}
					}
					else
					{
						p.sendMessage("Malformed sign");
						p.sendMessage(line1);
						
					}
			}

		}
				
	}


	
	
	public boolean signAddsPlayersToRoom(BlockState sign, int amount)//handles adding an extra player to the sign. Returns true if the room is full. //REQUIRES CHECKING IF PROPER SIGN FIRST
	{
		int thisSign = sign.hashCode();

			int currentCount = signSlotsUsed.get(thisSign);  //get the current amount of players
			int maxPlayers = Integer.parseInt(plugin.getConfig().getString("Arena.max-players"));
			boolean roomFull = false;
			
			currentCount += amount; //add players. Or subtract if a negative number is passed 
			if(currentCount < 0)currentCount = 0;
			if(currentCount > maxPlayers)
			{
				currentCount = maxPlayers;
				roomFull = true;
			}
			
			signSlotsUsed.put(thisSign, currentCount); //update the value in hash
			
			return roomFull; // you can determine if by adding a player it fills the room to stop adding more.
			// I initially used:    return (currentCount == maxPlayers) 
			// but this didn't allow me to tell the difference between already full, and just been filled.
		
	

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
		String configLine1 = plugin.getConfig().getString("Signs.Lobby.Line-1");
		String configLine2 = plugin.getConfig().getString("Signs.Lobby.Line-2");
		String configLine3 = plugin.getConfig().getString("Signs.Lobby.Line-3");
		String configLine4 = plugin.getConfig().getString("Signs.Lobby.Line-4");
		String maxPlayers = plugin.getConfig().getString("Arena.max-players");
		String currentPlayers = "" +  signSlotsUsed.get(thisSign.hashCode()); 
		
		if(signIsValid(thisSign))
        {
        
       
        	thisSign.setLine(0, ChatColor.translateAlternateColorCodes('&', configLine1));
        	thisSign.setLine(1, ChatColor.translateAlternateColorCodes('&', configLine2.replace("%amount%", "Players: " + currentPlayers + "/" + maxPlayers)));
        	thisSign.setLine(2, ChatColor.translateAlternateColorCodes('&', configLine3));
        	thisSign.setLine(3, ChatColor.translateAlternateColorCodes('&', configLine4));
        	
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
		String topLine = ChatColor.stripColor(thisSign.getLine(0));
        if(topLine.toLowerCase().contains("[paintball]"))
        {
        	return true;
        }
        return false;
    }
	
}