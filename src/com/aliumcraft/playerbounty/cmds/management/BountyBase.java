package com.aliumcraft.playerbounty.cmds.management;

import com.aliumcraft.playerbounty.Main;

public abstract class BountyBase {

	protected Main plugin = Main.getInstance();
	
	public abstract boolean runCheck();
	
}
