/**
 * ███████████████████████████████████████████████████████████████████
 * ██░░░░░░░░░░░░░░█░░░░░░░░░░░░░░█░░░░░░░░░░░░░░░░███░░░░░░░░░░░░░░██
 * ██░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀▄▀▄▀▄▀▄▀▄▀░░███░░▄▀▄▀▄▀▄▀▄▀░░██
 * ██░░▄▀░░░░░░░░░░█░░▄▀░░░░░░▄▀░░█░░▄▀░░░░░░░░▄▀░░███░░▄▀░░░░░░░░░░██
 * ██░░▄▀░░█████████░░▄▀░░██░░▄▀░░█░░▄▀░░████░░▄▀░░███░░▄▀░░██████████
 * ██░░▄▀░░█████████░░▄▀░░██░░▄▀░░█░░▄▀░░░░░░░░▄▀░░███░░▄▀░░░░░░░░░░██
 * ██░░▄▀░░█████████░░▄▀░░██░░▄▀░░█░░▄▀▄▀▄▀▄▀▄▀▄▀░░███░░▄▀▄▀▄▀▄▀▄▀░░██
 * ██░░▄▀░░█████████░░▄▀░░██░░▄▀░░█░░▄▀░░░░░░▄▀░░░░███░░▄▀░░░░░░░░░░██
 * ██░░▄▀░░█████████░░▄▀░░██░░▄▀░░█░░▄▀░░██░░▄▀░░█████░░▄▀░░██████████
 * ██░░▄▀░░░░░░░░░░█░░▄▀░░██░░▄▀░░█░░▄▀░░██░░▄▀░░░░███░░▄▀░░░░░░░░░░██
 * ██░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀░░██░░▄▀▄▀░░███░░▄▀▄▀▄▀▄▀▄▀░░██
 * ██░░░░░░░░░░░░░░█░░░░░░░░░░░░░░█░░░░░░██░░░░░░░░███░░░░░░░░░░░░░░██
 * ███████████████████████████████████████████████████████████████████
 *
 * Copyright © AliumCraft and others - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Charles Cullen (AliumCraft)
 */
package org.kcpdev.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

public class WorldGuardUtils {
	
	
	public boolean isPvPAllowed(Entity en) {
		if(Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null 
				&& Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			int x = en.getLocation().getBlockX();
			int y = en.getLocation().getBlockY();
			int z = en.getLocation().getBlockZ();
			
			Location loc = new Location(en.getWorld(), x,y,z);
			ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(en.getWorld()).getApplicableRegions(loc);
			
			if(set.queryState(null, DefaultFlag.PVP) == StateFlag.State.DENY) return false;
		}
		
		return true;
	}
	
	public boolean isBreakAllowed(Entity en) {
		if(Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null 
				&& Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			int x = en.getLocation().getBlockX();
			int y = en.getLocation().getBlockY();
			int z = en.getLocation().getBlockZ();
			
			Location loc = new Location(en.getWorld(), x,y,z);
			ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(en.getWorld()).getApplicableRegions(loc);
			
			if(set.queryState(null, DefaultFlag.BLOCK_BREAK) == StateFlag.State.DENY) return false;
		}
		
		return true;
	}
	
	public boolean allowsExplotions(Entity en) {
		if(Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null 
				&& Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			int x = en.getLocation().getBlockX();
			int y = en.getLocation().getBlockY();
			int z = en.getLocation().getBlockZ();
			
			Location loc = new Location(en.getWorld(), x,y,z);
			ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(en.getWorld()).getApplicableRegions(loc);
			
			if(set.queryState(null, DefaultFlag.OTHER_EXPLOSION) == StateFlag.State.DENY) return false;
		}
		
		return true;
	}
	
	
}
