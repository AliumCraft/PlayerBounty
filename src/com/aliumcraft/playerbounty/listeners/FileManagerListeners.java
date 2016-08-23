package com.aliumcraft.playerbounty.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class FileManagerListeners extends ListenerBase {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		
		plugin.loadPlayerFile(p);
	}
	
}
