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
			int amount = Integer.valueOf(args[2]);
			Player p = (Player) sender;
			
			BountyAdd ba = new BountyAdd(p, args[1], amount);
			if(!ba.checkForErrors()) return false;
			ba.run();
			
			return true;
		}
		
		
		/* TIMER */
		if(args[0].equalsIgnoreCase("timer") && args.length == 1) {
			if(!checkIfSenderIsPlayer(sender)) return false;
			
			
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
			
		}
			
		
		/* SET */
		if(args[0].equalsIgnoreCase("set") && args.length == 3) {
			if(!sender.hasPermission(Permissions.Admin.getPerm())) {
				Messages.NoPermission.msg(sender);
				return false;
			}
			
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
