package com.aliumcraft.playerbounty.utils;

import org.bukkit.event.Listener;

import com.aliumcraft.playerbounty.Main;

public class ListenerBase implements Listener {

	protected Main plugin = Main.getInstance();
	protected ItemStackUtils isUtils = new ItemStackUtils();
	
}
