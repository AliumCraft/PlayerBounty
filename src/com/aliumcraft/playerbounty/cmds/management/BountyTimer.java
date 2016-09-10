package com.aliumcraft.playerbounty.cmds.management;

import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.utils.Messages;

public class BountyTimer extends BountyBase {

	public BountyTimer(Player p) {
		this.p = p;
	}
	
	private Player p;

	@Override
	public boolean checkForErrors() {
		if(!plugin.getConfig().getBoolean("Features.BountyTimer")) {
			Messages.BountyTimer_NotEnabled.msg(p);
			return false;
		}
		
		if(!com.aliumcraft.playerbounty.utils.BountyTimer.timeLeft.containsKey(p)) {
			Messages.BountyTimer_NoBounty.msg(p);
			return false;
		}
		
		return true;
	}

	@Override
	public void run() {
		String msg = Messages.BountyTimer_Expired.toString();
		int current = com.aliumcraft.playerbounty.utils.BountyTimer.timeLeft.get(p);
		
		msg = msg.replace("#", plugin.formatTime(current));
		p.sendMessage(msg);
	}
}
