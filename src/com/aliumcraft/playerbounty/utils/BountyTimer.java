package com.aliumcraft.playerbounty.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.Main;

public class BountyTimer {
	
	private Main plugin = Main.getInstance();
	public static HashMap<Player,Integer> timeLeft = new HashMap<Player,Integer>();
	private int countdownTimer;
	
	public void startCountdown(final Player p, int time) {
		this.countdownTimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int i;
			
			public void run() {
				BountyTimer.timeLeft.put(p, Integer.valueOf(i));
				
				this.i -= 1;
				
				if(this.i <= 0) {
					cancel();
					BountyTimer.timeLeft.remove(p);
				}
			}
		}, 0L, 20L);
	}
	
	public void cancel() {
		Bukkit.getScheduler().cancelTask(this.countdownTimer);
	}
	
}
