package com.aliumcraft.playerbounty.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aliumcraft.playerbounty.Main;

public class BountyAPI {
	
	/**This will return a double with
	 * the current bounty that the
	 * player has on them. If MySQL is
	 * enabled it will be called from the
	 * database, otherwise it will be
	 * called from bounties.yml
	 * 
	 * @param p - the online player.
	 */
	public static double getBounty(Player p) {
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bounty FROM " + Main.DB_TABLE + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getDouble("bounty");
			} catch (SQLException ex) {
				ex.printStackTrace();
				
				return 0;
			}
		} else {
			double amount;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				amount = Main.bounty.getDouble("Bounties." + p.getUniqueId() + ".Amount");
			} else {
				amount = 0;
			}
			
			return amount;
		}
	}
	
	/**This will return a double with
	 * the current bounty that the
	 * player has on them. If MySQL is
	 * enabled it will be called from the
	 * database, otherwise it will be
	 * called from bounties.yml.
	 * 
	 * @param p - the offline player.
	 */
	public static double getBounty(OfflinePlayer p) { 
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bounty FROM " + Main.DB_TABLE + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getDouble("bounty");
			} catch (SQLException ex) {
				ex.printStackTrace();
				
				return 0;
			}
		} else {
			double amount;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				amount = Main.bounty.getDouble("Bounties." + p.getUniqueId() + ".Amount");
			} else {
				amount = 0;
			}
			
			return amount;
		}
	}
	
	/**This will return the total
	 * bounty that the player has
	 * had. If MySQL is enabled it
	 * will be called from the database
	 * otherwise it will called from
	 * bounties.yml.
	 * 
	 * @param p - the online player.
	 */
	public static double getTotalBounty(Player p) {
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT TotalBounty FROM " + Main.DB_TABLE + " WHERE uuid ='" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getDouble("TotalBounty");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			double amount = 0D;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				amount = Main.bounty.getDouble("Bounties." + p.getUniqueId() + ".TotalBounty");
			}			
			return amount;
		}
	}
	
	/**This will return the total
	 * bounty that the player has
	 * had. If MySQL is enabled it
	 * will be called from the database
	 * otherwise it will called from
	 * bounties.yml.
	 * 
	 * @param p - the offline player.
	 */
	public static double getTotalBounty(OfflinePlayer p) {
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT TotalBounty FROM " + Main.DB_TABLE + " WHERE uuid ='" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getDouble("TotalBounty");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			double amount = 0D;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				amount = Main.bounty.getDouble("Bounties." + p.getUniqueId() + ".TotalBounty");
			}			
			return amount;
		}
	}
	
	/**This will return amount of
	 * bounties this player has
	 * claimed. If MySQL is enabled it
	 * will be called from the database
	 * otherwise it will called from
	 * bounties.yml.
	 * 
	 * @param p - the online player.
	 */
	public static int getBountiesClaimed(Player p) {
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bountiesClaimed FROM " + Main.DB_TABLE + " WHERE uuid ='" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getInt("bountiesClaimed");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			int amount = 0;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				amount = Main.bounty.getInt("Bounties." + p.getUniqueId() + ".BountiesClaimed");
			}
			return amount;
		}
	}
	
	/**This will return amount of
	 * bounties this player has
	 * claimed. If MySQL is enabled it
	 * will be called from the database
	 * otherwise it will called from
	 * bounties.yml.
	 * 
	 * @param p - the offline player.
	 */
	public static int getBountiesClaimed(OfflinePlayer p) {
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bountiesClaimed FROM " + Main.DB_TABLE + " WHERE uuid ='" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getInt("bountiesClaimed");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			int amount = 0;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				amount = Main.bounty.getInt("Bounties." + p.getUniqueId() + ".BountiesClaimed");
			}
			return amount;
		}
	}
	
	/**This will add a bounty
	 * to the specified player.
	 * Depending on what is
	 * currently enabled is how
	 * it will be saved.
	 * 
	 * @param p - the online player.
	 * @param amount - the amount to increase the bounty by.
	 */
	public static void addBounty(Player p, double amount) {
		double total = BountyAPI.getTotalBounty(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			double bounty = BountyAPI.getBounty(p) + amount;
			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'"; 
			
			try {
				Main.s.executeUpdate(sql1);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				Main.s.executeUpdate(sql2);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			double bounty = amount + Main.bounty.getDouble("Bounties." + p.getUniqueId() + ".Amount");
			
			Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", bounty);
			Main.bounty.set("Bounties." + p.getUniqueId() + ".TotalBounty", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will add a bounty
	 * to the specified player.
	 * Depending on what is
	 * currently enabled is how
	 * it will be saved.
	 * 
	 * @param p - the offline player.
	 * @param amount - the amount to increase the bounty by.
	 */
	public static void addBounty(OfflinePlayer p, double amount) {
		double total = BountyAPI.getTotalBounty(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			double bounty = BountyAPI.getBounty(p) + amount;
			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'"; 
			
			try {
				Main.s.executeUpdate(sql1);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				Main.s.executeUpdate(sql2);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			double bounty = amount + Main.bounty.getDouble("Bounties." + p.getUniqueId() + ".Amount");
			
			Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", bounty);
			Main.bounty.set("Bounties." + p.getUniqueId() + ".TotalBounty", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will take the amount
	 * from the specified player.
	 * Depending on what is
	 * currently enabled is how
	 * it will be saved.
	 * 
	 * @param p - the online player.
	 * @param amount - the amount to decrease the bounty by.
	 */
	public static void takeBounty(Player p, double amount) {
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			double bounty = BountyAPI.getBounty(p) - amount;
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			double bounty = amount;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				bounty = Main.bounty.getDouble("Bounties." + p.getUniqueId()) - bounty;
				
				Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", bounty);
				Main.saveBountyStatic();
			} else {
				bounty = 0;
				
				Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", bounty);
				Main.saveBountyStatic();
			}
		}
	}
	
	/**This will take the amount
	 * from the specified player.
	 * Depending on what is
	 * currently enabled is how
	 * it will be saved.
	 * 
	 * @param p - the offline player.
	 * @param amount - the amount to decrease the bounty by.
	 */
	public static void takeBounty(OfflinePlayer p, double amount) {
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			double bounty = BountyAPI.getBounty(p) - amount;
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			double bounty = amount;
			
			if(Main.bounty.contains("Bounties." + p.getUniqueId())) {
				bounty = Main.bounty.getDouble("Bounties." + p.getUniqueId()) - bounty;				
				Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", bounty);
				Main.saveBountyStatic();
			} else {
				bounty = 0;
				
				Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", bounty);
				Main.saveBountyStatic();
			}
		}
	}
	
	/**This will set the bounty
	 * for the specified player.
	 * Depending on what is
	 * currently enabled is how
	 * it will be saved.
	 * 
	 * @param p - the online player.
	 * @param amount - the amount to set the bounty to.
	 */
	public static void setBounty(Player p, double amount) {
		double total = BountyAPI.getTotalBounty(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + amount + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'"; 
			
			try {
				Main.s.executeUpdate(sql1);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				Main.s.executeUpdate(sql2);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			
			Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", amount);
			Main.bounty.set("Bounties." + p.getUniqueId() + ".TotalBounty", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will set the bounty
	 * for the specified player.
	 * Depending on what is
	 * currently enabled is how
	 * it will be saved.
	 * 
	 * @param p - the offline player.
	 * @param amount - the amount to set the bounty to.
	 */
	public static void setBounty(OfflinePlayer p, double amount) {
		double total = BountyAPI.getTotalBounty(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + amount + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'"; 
			
			try {
				Main.s.executeUpdate(sql1);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				Main.s.executeUpdate(sql2);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			
			Main.bounty.set("Bounties." + p.getUniqueId() + ".Amount", amount);
			Main.bounty.set("Bounties." + p.getUniqueId() + ".TotalBounty", total);
			Main.saveBountyStatic();
		}
	}
}
