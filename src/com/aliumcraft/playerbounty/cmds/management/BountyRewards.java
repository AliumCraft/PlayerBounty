package com.aliumcraft.playerbounty.cmds.management;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aliumcraft.playerbounty.utils.Messages;

public class BountyRewards extends BountyBase {

	public BountyRewards(Player p, int num) {
		this.p = p;
		this.num = num;
	}
	
	private Player p;
	private int num;
	
	@Override
	public boolean checkForErrors() {
		if(!plugin.getConfig().contains("BountyStreaks." + num)) {
			Messages.BountyRewards_None.msg(p);
			return false;
		}
		
		
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		String msg1 = Messages.BountyRewards_Filler1.toString();
		String msg2 = Messages.BountyRewards_Filler2.toString();
		String msg3 = Messages.BountyRewards_Filler3.toString();
		String msg4 = Messages.BountyRewards_Filler4.toString();
		String head = Messages.BountyRewards_Header.toString();
		String foot = Messages.BountyRewards_Footer.toString();
		String spac = Messages.BountyRewards_Spacer.toString();
		ConfigurationSection cs = plugin.getConfig().getConfigurationSection("BountyStreaks." + num);
		int money = cs.getInt("Cash");
		
		msg1 = msg1.replace("{x}", "" + num);
		msg2 = msg2.replace("{amount}", ""+money);
		
		p.sendMessage(head);
		p.sendMessage(spac);
		p.sendMessage(msg1);
		p.sendMessage(msg2);		
		p.sendMessage(msg3);
		
		for(String s : cs.getConfigurationSection("Items").getKeys(false)) {
			String msg4Clone = msg4;
			String id = cs.getString("Items." + s + ".Type");
			int amount = cs.getInt("Items." + s + ".Amount");
			int idNum = 0;
			int meta = 0;		
			
			if(id.contains(":")) {
				String[] stringSplit = id.split(":");
				meta = Integer.valueOf(stringSplit[1]);
				idNum = Integer.valueOf(stringSplit[0]);
			} else {
				idNum = Integer.valueOf(id);
			}
			
			ItemStack is = new ItemStack(Material.getMaterial(idNum), amount, (byte) meta);
			String replace = is.getType().toString();
			replace = replace.replace("_", " ");
			
			msg4Clone = msg4Clone.replace("{am}", ""+amount);
			msg4Clone = msg4Clone.replace("{item}", replace);
			p.sendMessage(msg4Clone);
		}
		
		p.sendMessage(spac);
		p.sendMessage(foot);
		
	}

}
