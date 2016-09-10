package com.aliumcraft.playerbounty.listeners;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.api.BountyManagement;
import com.aliumcraft.playerbounty.utils.ListenerBase;
import com.aliumcraft.playerbounty.utils.Messages;

@SuppressWarnings("deprecation")
public class ClaimListeners extends ListenerBase {	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		Entity killerEntity = killed.getKiller();
		
		if(!killed.isDead() || (!(killerEntity instanceof Player))) return;
		Player killer = (Player) killed.getKiller();
		
		if(killer.getName().equals(killed.getName())) return;
		if(!killer.hasPermission("pbounty.claim")) return;
		
		BountyManagement bm = new BountyManagement(killed);
		double amount = bm.getBounty();
		String formattedAmount = plugin.format(amount);
		
		if(amount == 0.0D) return;
		
		claimBounty(killer, killed);
		bountyStreaks(killer);
		
		if(!plugin.getConfig().getBoolean("Features.HeadDrops")) return;
		
		String name = plugin.getConfig().getString("HeadDrops.Name");
		List<String> lore = plugin.getConfig().getStringList("HeadDrops.Lore");
		List<String> list = new ArrayList<String>();
		Date date = new Date();
		SimpleDateFormat sdt = new SimpleDateFormat("MMMMMMMMM d, yyyy");
		
		name = name.replace("{p}", killed.getName());
		
		for(String s : lore) {
			String item = killer.getInventory().getItemInHand().getItemMeta().getDisplayName();
			
			if(item != null) {
				s = s.replace("{w}", item);
			} else {
				Material nullItem = killer.getInventory().getItemInHand().getType();
				
				if(nullItem == Material.AIR) {
					s = s.replace("{w}", "&fBARE FISTS");
				} else {
					s = s.replace("{w}", "&f" + nullItem.toString().replace("_", " "));
				}
			}
			
			s = s.replace("{k}", killer.getName());
			s = s.replace("{d}", sdt.format(date));
			s = s.replace("{a}", formattedAmount);			
			
			list.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		
		ItemStack skull = isUtils.customItem(397, 1, (byte) 3, name, list);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		
		sm.setOwner(killed.getName());
		skull.setItemMeta(sm);
		
		Location loc = killed.getLocation();
		
		list.clear();
		loc.getWorld().dropItemNaturally(loc, skull);
	}
	
	private void claimBounty(Player killer, Player killed) {
		BountyManagement bm = new BountyManagement(killed);
		double amount = bm.getBounty();
		String formattedAmount = plugin.format(amount);
		double minBroadcast = plugin.getConfig().getDouble("Settings.MinBountyToBroadcast");
		
		if(amount >= minBroadcast) {
			for(String s : plugin.getConfig().getStringList("Messages.BountyClaim")) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', s)
						.replace("%amount%", formattedAmount)
						.replace("%player%", killer.getName())
						.replace("%bounty%", killed.getName()));
			}
		}
		
		String msg = Messages.MoneyGiven.toString();
		msg = msg.replace("%amount%", formattedAmount);
		
		killer.sendMessage(msg);
		Main.econ.depositPlayer(killer, amount);
		
		bm.takeBounty(bm.getBounty());
		bm.setStreak(0);
	}
	
	private void bountyStreaks(Player p) {
		List<String> rewards = new ArrayList<String>();
		List<String> broadcastNums = plugin.getConfig().getStringList("StreakBroadcasts");
		BountyManagement bm = new BountyManagement(p);
		bm.addStreak(1);
		bm.addBountiesClaimed(1);
		
		int current = bm.getCurrentStreak();
		
		if(plugin.getConfig().contains("BountyStreaks." + current)) {
			String path = "BountyStreaks." + current + ".";
			double cash = plugin.getConfig().getDouble(path + "Cash");
			
			if(cash != 0.0D) {
				Main.econ.depositPlayer(p, cash);
				String msg = Messages.MoneyGiven.toString();
				msg = msg.replace("%amount%", plugin.format(cash));
				
				p.sendMessage(msg);
			}
			
			for(String s : plugin.getConfig().getConfigurationSection(path + "Items").getKeys(false)) {
				rewards.add(s);
			}
			
			for(int i = 0; i < rewards.size(); i++) {
				path = "BountyStreaks." + current + ".Items." + rewards.get(i) + ".";
				
				String type = plugin.getConfig().getString(path + "Type");
				int amount = plugin.getConfig().getInt(path + "Amount");
				String name = plugin.getConfig().getString(path + "Name");
				List<String> lore = plugin.getConfig().getStringList(path + "Lore");
				int idNum = 0;
				int meta = 0;		
				
				if(type.contains(":")) {
					String[] stringSplit = type.split(":");
					meta = Integer.valueOf(stringSplit[1]);
					idNum = Integer.valueOf(stringSplit[0]);
				} else {
					idNum = Integer.valueOf(type);
				}
				
				ItemStack is = isUtils.customItem(idNum, amount, (byte) meta, name, lore);
				
				if(p.getInventory().firstEmpty() != -1) {
					p.getInventory().addItem(is);
				} else {
					p.getWorld().dropItemNaturally(p.getLocation(), is);
				}
			}
		}
		
		String msg = Messages.BountyStreaks_BroadcastMessage.toString();
		msg = msg.replace("{x}", ""+current);
		msg = msg.replace("%player%", p.getName());
		
		if(broadcastNums.contains(""+current)) {
			Bukkit.broadcastMessage(msg);
		}
		
	}
}
