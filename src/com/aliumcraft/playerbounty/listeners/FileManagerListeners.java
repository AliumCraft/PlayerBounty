package com.aliumcraft.playerbounty.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aliumcraft.playerbounty.utils.ListenerBase;

public class FileManagerListeners extends ListenerBase {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		
		plugin.loadPlayerFile(p);
	}
	
}
