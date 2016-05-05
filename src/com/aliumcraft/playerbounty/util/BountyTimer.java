package com.aliumcraft.playerbounty.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.Main;

public class BountyTimer {
	private Main plugin;
	
	public BountyTimer(Main i) {
		this.plugin = i;
	}
	
	public static HashMap<Player,Integer> timeLeft = new HashMap<Player,Integer>();
	private int countdownTimer;
	
	public void startCountdown(final Player p, final int time, final String msg) {		
		this.countdownTimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int i = time;
			
			public void run() {
				BountyTimer.timeLeft.put(p, i);
				
				this.i--;
				if(this.i <= 0) {
					BountyTimer.this.cancel();
					timeLeft.remove(p);
				}
			}
		}, 0L, 20L);
	}
	
	public void cancel() {
		Bukkit.getScheduler().cancelTask(this.countdownTimer);
	}
	
}
