package com.aliumcraft.playerbounty.cmds.management;

import com.aliumcraft.playerbounty.Main;

public abstract class BountyBase {

	protected Main plugin = Main.getInstance();
	
	public abstract boolean checkForErrors();
	public abstract void run();
	
	public int getMaxBounty() {
		return plugin.getConfig().getInt("Settings.MaxBounty");
	}
	
	public int getMinBounty() {
		return plugin.getConfig().getInt("Settings.MinBounty");
	}
	
}
