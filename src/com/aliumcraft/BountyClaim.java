package com.aliumcraft;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.md_5.bungee.api.ChatColor;

public class BountyClaim implements Listener {
	Main plugin;
	
	public BountyClaim(Main pl) {
		this.plugin = pl;
	}
	
	@SuppressWarnings("deprecation")
	private void bountyClaim(Player killer, Player killed) {
		String amount = String.valueOf(plugin.getConfig().getDouble("Bounties." + killed.getUniqueId()));
		Double amountD = plugin.getConfig().getDouble("Bounties." + killed.getUniqueId());
		List<String> bountyList = plugin.getConfig().getStringList("BountyList");
		
		if(plugin.getConfig().getDouble("Bounties." + killed.getUniqueId()) >=
				plugin.getConfig().getDouble("Bounty.Min-Bounty-Kill-Broadcast")) {
			for(String s : plugin.getStrings("Messages.Bounty-Claim")) {
				plugin.bct(ChatColor.translateAlternateColorCodes('&', s)
						.replace("%amount%", amount)
						.replace("%player%", killer.getName())
						.replace("%bounty%", killed.getName()));
			}
			plugin.msg(killer, plugin.getString("Messages.Money-Given")
					.replace("%amount%", amount));
		} else {
			plugin.msg(killer, plugin.getString("Messages.Money-Given")
					.replace("%amount%", amount));
		}
		Main.econ.depositPlayer(killer.getName(), amountD);
		
		plugin.getConfig().set("Bounties." + killed.getUniqueId(), null);
		bountyList.remove(killed.getName());
		plugin.getConfig().set("BountyList", bountyList);
		plugin.saveConfig();
	}
	
	@EventHandler
	public void onKillEvent(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		Player killer = e.getEntity().getKiller();
		e.getEntity().getWorld();
		
		if(plugin.getConfig().contains("Bounties." + killed.getUniqueId())) {
			bountyClaim(killer, killed);
		}
	}
}
