package com.aliumcraft.playerbounty.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kcpdev.utils.commands.CommandUtils;

import com.aliumcraft.playerbounty.Main;
import com.aliumcraft.playerbounty.cmds.management.BountyAdd;
import com.aliumcraft.playerbounty.cmds.management.BountyBase;
import com.aliumcraft.playerbounty.cmds.management.BountyGet;
import com.aliumcraft.playerbounty.cmds.management.BountyList;
import com.aliumcraft.playerbounty.cmds.management.BountyRewards;
import com.aliumcraft.playerbounty.cmds.management.BountySet;
import com.aliumcraft.playerbounty.cmds.management.BountyTake;
import com.aliumcraft.playerbounty.cmds.management.BountyTimer;
import com.aliumcraft.playerbounty.utils.Messages;
import com.aliumcraft.playerbounty.utils.Permissions;

public class BountyCmd extends BountyBase implements CommandUtils {
	
	private Main plugin = Main.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!Permissions.hasPermissionForCommand(sender)) {			
			Messages.NoPermission.msg(sender);
			return false;
		}
		
		/* HELP */
		if(args.length == 0) {
			sendHelpMessage(sender);
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("help")) {
			sendHelpMessage(sender);
			
			return true;
		}
		
		
		/* GET */
		if(args[0].equalsIgnoreCase("get") && args.length == 2) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			Player p = (Player) sender;
			BountyGet bg = new BountyGet(p, args[1]);
			
			if(!bg.checkForErrors()) return false;
			bg.run();
			
			return true;
		}
		
		
		/* LIST */
		if(args[0].equalsIgnoreCase("list") && args.length == 1) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			
			BountyList bl = new BountyList((Player) sender);
			
			if(!bl.checkForErrors()) return false;
			bl.run();
			
			return true;
		}
		
		
		/* ADD */
		if(args[0].equalsIgnoreCase("add") && args.length == 3) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			if(!checkIfStringIsNumber(args[2])) return false;
			double amount = Integer.valueOf(args[2]);
			Player p = (Player) sender;
			BountyAdd ba = new BountyAdd(p, args[1], amount);
			
			if(!ba.checkForErrors()) return false;
			ba.run();
			
			return true;
		}
		
		
		/* TIMER */
		if(args[0].equalsIgnoreCase("timer") && args.length == 1) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			Player p = (Player) sender;
			
			BountyTimer bt = new BountyTimer(p);
			
			if(!bt.checkForErrors()) return false;
			bt.run();
			
			return true;
		}
		
		
		/* STREAK */
		if(args[0].equalsIgnoreCase("streak") && (args.length == 1 || args.length == 2)) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			
		}
		
		
		/* RELOAD */
		if(args[0].equalsIgnoreCase("reload") && args.length == 1) {
			if(!sender.hasPermission(Permissions.Admin.getPerm())) {
				Messages.NoPermission.msg(sender);
				return false;
			}
			
			plugin.reloadFiles();
			Messages.Reload.msg(sender);
			return true;
		}
		
		
		/* TAKE */
		if(args[0].equalsIgnoreCase("take") && args.length == 3) {
			if(!sender.hasPermission(Permissions.Admin.getPerm())) {
				Messages.NoPermission.msg(sender);
				return false;
			}
			
			if(!checkIfStringIsNumber(args[2])) return false;
			double amount = Double.valueOf(args[2]);
			BountyTake bt = new BountyTake(sender, args[1], amount);
			
			if(!bt.checkForErrors()) return false;
			bt.run();
			
			return true;
		}
			
		
		/* SET */
		if(args[0].equalsIgnoreCase("set") && args.length == 3) {
			if(!sender.hasPermission(Permissions.Admin.getPerm())) {
				Messages.NoPermission.msg(sender);
				return false;
			}
			
			if(!checkIfStringIsNumber(args[2])) return false;
			double amount = Double.valueOf(args[2]);
			
			BountySet bs = new BountySet(sender, args[1], amount);
			
			if(!bs.checkForErrors()) return false;
			bs.run();
			
			return true;
			
		}
		
		
		/* REWARDS */
		if(args[0].equalsIgnoreCase("rewards") && args.length == 1) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			
			Messages.BountyRewards_Help.msg(sender);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("rewards") && args.length == 2) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			if(!checkIfStringIsNumber(args[1])) return false;
			Player p = (Player) sender;
			BountyRewards br = new BountyRewards(p, Integer.valueOf(args[1]));
			
			if(!br.checkForErrors()) return false;
			br.run();
			
			return true;
			
		}
			
		sendHelpMessage(sender);
		
		return true;
	}

	@Override
	public boolean checkForErrors() {
		return false;
	}

	@Override
	public void run() {}
	
}
