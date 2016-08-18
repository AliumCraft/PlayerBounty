package com.aliumcraft.playerbounty;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	
	public void onEnable() {
		instance = this;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	private void loadMyFiles() {
		
	}
}
