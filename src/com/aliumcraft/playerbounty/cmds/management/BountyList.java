package com.aliumcraft.playerbounty.cmds.management;

import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.inventories.BountyListInv;

public class BountyList extends BountyBase {

	private BountyListInv bountyListInv;
	
	public BountyList(Player p) {
		this.bountyListInv = new BountyListInv(p);
	}
	
	@Override
	public boolean checkForErrors() {
		return true;
	}

	@Override
	public void run() {
		bountyListInv.openInventory(1);
	}

}
