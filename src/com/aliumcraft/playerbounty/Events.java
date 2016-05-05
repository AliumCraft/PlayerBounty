package com.aliumcraft.playerbounty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.aliumcraft.playerbounty.util.BountyList;

public class Events implements Listener {
	Main plugin;
	BountyList bl;
	private List<String> bountylist = new ArrayList<String>();
	
	public Events(Main main, BountyList bountylist) {
		this.plugin = main;
		this.bl = bountylist;
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		
		createSqlEntryOnLogin(p);
		if(hasBounty(p)) {			
			if(plugin.getConfig().getBoolean("Bounty.Particles")) {
				schedule(p);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(hasBounty(p)) {
			if(plugin.getConfig().getBoolean("Bounty.Particles")) {
				schedule(p);
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location to = e.getTo();
		Location from = e.getFrom();
		
		if(from.getBlockX() != to.getBlockX() ||
				from.getBlockY() != to.getBlockY() ||
				from.getBlockZ() != to.getBlockZ()) { 
			if(hasBounty(p)) {	
				if(plugin.getConfig().getBoolean("Bounty.Particles")) {
					schedule(p);
				}
			}			
		}
	}
	
	public boolean hasBounty(Player p) {
		if(plugin.getConfig().getBoolean("Database.Enabled")) {
			return true;
		} else {
			if(Main.bounty.getDouble("Bounties." + p.getUniqueId()) != 0) {
				return true;
			}
			return false;
		}
	}
	
	private void schedule(Player p) {
		if(!bountylist.contains(p.getName())) {
			bountylist.add(p.getName());
			
			new BukkitRunnable() {
				public void run() {
					if(bountylist.contains(p.getName())) {
						if(hasBounty(p)) {
							World w = p.getWorld();
							
							w.playEffect(p.getLocation(), Effect.STEP_SOUND, Material.GLASS);
							w.playEffect(p.getEyeLocation(), Effect.STEP_SOUND, Material.GLASS);
						} else {
							bountylist.remove(p.getName());
							cancel();
						}
					}
				}
			}.runTaskTimer(plugin, 0L, 600L);
		}
	}
	
	private void createSqlEntryOnLogin(Player p) {
		if(plugin.getConfig().getBoolean("Database.Enabled")) {
			String sql = "INSERT INTO " + Main.DB_TABLE + " (uuid, name, bounty, bountiesClaimed, TotalBounty) VALUES ('"
					+ p.getUniqueId().toString().replace("-", "") + "', '" + p.getName() + "', 0, 0, 0)";
			try {
				Main.s.executeUpdate(sql);
			} catch (Exception e) {
				plugin.getLogger().info("Player is already stored.");
				
				String setName = "UPDATE " + Main.DB_TABLE + " SET name=" + p.getName() + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
				try {
					Main.s.executeUpdate(setName);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			if(!Main.bounty.contains("Bounties." + p.getUniqueId())) {
				Main.bounty.set("Bounties." + p.getUniqueId() + ".Name", p.getName());
				Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", 0);
				Main.bounty.set("Bounties." + p.getUniqueId() + ".TotalBounty", 0);
				Main.bounty.set("Bounties." + p.getUniqueId() + ".BountiesClaimed", 0);
				plugin.saveBounty();
			}
		}
	}
}