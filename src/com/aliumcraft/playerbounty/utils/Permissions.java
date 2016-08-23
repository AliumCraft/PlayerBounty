package com.aliumcraft.playerbounty.utils;

import org.bukkit.command.CommandSender;

public enum Permissions {

	Default("pbounty.default"),
	Admin("pbounty.admin"),
	Claim("pbounty.claim");
	
	private String perm;
	
	private Permissions(String perm) {
		this.perm = perm;
	}
	
	public String getPerm() {
		return perm;
	}
	
	public static boolean hasPermissionForCommand(CommandSender p) {
		if(p.hasPermission(Default.getPerm())) {
			
			return true;
			
		} else if(p.hasPermission(Admin.getPerm())) {	
			
			return true;
			
		} else {
			
			return false;
			
		}	
	}
}
