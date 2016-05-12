package com.aliumcraft.playerbounty.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bounty FROM " + Main.DB_TABLE + " WHERE uuid = '" + uuid + "'";
			
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
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getDouble("Bounties." + uuid + ".Amount");
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bounty FROM " + Main.DB_TABLE + " WHERE uuid = '" + uuid + "'";
			
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
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getDouble("Bounties." + uuid + ".Amount");
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
	public static int getTotalBounty(Player p) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT TotalBounty FROM " + Main.DB_TABLE + " WHERE uuid ='" + uuid + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getInt("TotalBounty");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			int amount = 0;
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getInt("Bounties." + uuid + ".TotalBounty");
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
	public static int getTotalBounty(OfflinePlayer p) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT TotalBounty FROM " + Main.DB_TABLE + " WHERE uuid ='" + uuid + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getInt("TotalBounty");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			int amount = 0;
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getInt("Bounties." + uuid + ".TotalBounty");
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bountiesClaimed FROM " + Main.DB_TABLE + " WHERE uuid ='" + uuid + "'";
			
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
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getInt("Bounties." + uuid + ".BountiesClaimed");
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT bountiesClaimed FROM " + Main.DB_TABLE + " WHERE uuid ='" + uuid + "'";
			
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
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getInt("Bounties." + uuid + ".BountiesClaimed");
			}
			return amount;
		}
	}
	
	/**This will return amount of
	 * bounties the player has killed
	 * without dying. If MySQL is enabled it
	 * will be called from the database
	 * otherwise it will called from
	 * bounties.yml.
	 * 
	 * @param p - the online player.
	 */
	public static int getBountyStreak(Player p) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT BountyStreak FROM " + Main.DB_TABLE + " WHERE uuid ='" + uuid + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getInt("BountyStreak");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			int amount = 0;
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getInt("Bounties." + uuid + ".BountyStreak");
			}
			return amount;
		}
	}
	
	/**This will return amount of
	 * bounties the player has killed
	 * without dying. If MySQL is enabled it
	 * will be called from the database
	 * otherwise it will called from
	 * bounties.yml.
	 * 
	 * @param p - the offline player.
	 */
	public static int getBountyStreak(OfflinePlayer p) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			String sql = "SELECT BountyStreak FROM " + Main.DB_TABLE + " WHERE uuid ='" + uuid + "'";
			
			try {
				ResultSet playerData = Main.s.executeQuery(sql);
				playerData.next();
				return playerData.getInt("BountyStreak");
			} catch (SQLException ex) {
				ex.printStackTrace();
				return 0;
			}
		} else {
			int amount = 0;
			
			if(Main.bounty.contains("Bounties." + uuid)) {
				amount = Main.bounty.getInt("Bounties." + uuid + ".BountyStreak");
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		double total = BountyAPI.getTotalBounty(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			double bounty = BountyAPI.getBounty(p) + amount;
			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + uuid + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + uuid + "'"; 
			
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
			double bounty = amount + Main.bounty.getDouble("Bounties." + uuid + ".Amount");
			
			Main.bounty.set("Bounties." + uuid + ".Amount", bounty);
			Main.bounty.set("Bounties." + uuid + ".TotalBounty", total);
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		double total = BountyAPI.getTotalBounty(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			double bounty = BountyAPI.getBounty(p) + amount;
			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + uuid + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + uuid + "'"; 
			
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
			double bounty = amount + Main.bounty.getDouble("Bounties." + uuid + ".Amount");
			
			Main.bounty.set("Bounties." + uuid + ".Amount", bounty);
			Main.bounty.set("Bounties." + uuid + ".TotalBounty", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will add a bounty
	 * to the specified players
	 * bounties claimed.
	 * 
	 * @param p - the online player.
	 * @param amount - the amount to increase the bountiesclaimed by.
	 */
	public static void addBountiesClaimed(Player p, int amount) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		int total = BountyAPI.getBountiesClaimed(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET bountiesClaimed=" + total + " WHERE uuid = '" + uuid + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
		} else {			
			Main.bounty.set("Bounties." + uuid + ".BountiesClaimed", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will add a bounty
	 * to the specified players
	 * bounties claimed.
	 * 
	 * @param p - the offline player.
	 * @param amount - the amount to increase the bountiesclaimed by.
	 */
	public static void addBountiesClaimed(OfflinePlayer p, int amount) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		int total = BountyAPI.getBountiesClaimed(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET bountiesClaimed=" + total + " WHERE uuid = '" + uuid + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
		} else {			
			Main.bounty.set("Bounties." + uuid + ".BountiesClaimed", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will add the amount
	 * to the specified players
	 * bounty kill streak. This
	 * will reset if the player
	 * dies.
	 * 
	 * @param p - The online player.
	 * @param amount - The amount to increase the bounty streak by. 
	 */
	public static void addBountyStreak(Player p, int amount) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		int total = BountyAPI.getBountyStreak(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET BountyStreak=" + total + " WHERE uuid = '" + uuid + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {			
			Main.bounty.set("Bounties." + uuid + ".BountyStreak", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will add the amount
	 * to the specified players
	 * bounty kill streak. This
	 * will reset if the player
	 * dies.
	 * 
	 * @param p - The online player.
	 * @param amount - The amount to increase the bounty streak by. 
	 */
	public static void addBountyStreak(OfflinePlayer p, int amount) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		int total = BountyAPI.getBountyStreak(p) + amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET BountyStreak=" + total + " WHERE uuid = '" + uuid + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {			
			Main.bounty.set("Bounties." + uuid + ".BountyStreak", total);
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		double current = BountyAPI.getBounty(p);
		double bounty = current - amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {			
			String sql;
			
			if(bounty > 0) {
				sql = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + uuid + "'";
			} else {
				sql = "UPDATE " + Main.DB_TABLE + " SET bounty=" + 0D + " WHERE uuid = '" + uuid + "'";
			}
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			
			if(bounty > 0) {
				Main.getBounty().set("Bounties." + uuid + ".Amount", bounty);
			} else {
				Main.getBounty().set("Bounties." + uuid + ".Amount", 0D);
			}
			Main.saveBountyStatic();
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		double current = BountyAPI.getBounty(p);
		double bounty = current - amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {			
			String sql;
			
			if(bounty > 0) {
				sql = "UPDATE " + Main.DB_TABLE + " SET bounty=" + bounty + " WHERE uuid = '" + uuid + "'";
			} else {
				sql = "UPDATE " + Main.DB_TABLE + " SET bounty=" + 0D + " WHERE uuid = '" + uuid + "'";
			}
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {
			
			if(bounty > 0) {
				Main.getBounty().set("Bounties." + uuid + ".Amount", bounty);
			} else {
				Main.getBounty().set("Bounties." + uuid + ".Amount", 0D);
			}
			Main.saveBountyStatic();
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		double total = BountyAPI.getTotalBounty(p) + amount;
		List<String> bountylist = Main.getBounty().getStringList("BountyList");
		
		if(!bountylist.contains(p.getName())) bountylist.add(p.getName());
		
		Main.getBounty().set("BountyList", bountylist);
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + amount + " WHERE uuid = '" + uuid + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + uuid + "'"; 
			
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
			
			Main.bounty.set("Bounties." + uuid + ".Amount", amount);
			Main.bounty.set("Bounties." + uuid + ".TotalBounty", total);
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
		String uuid = p.getUniqueId().toString().replace("-", "");
		double total = BountyAPI.getTotalBounty(p) + amount;
		List<String> bountylist = Main.getBounty().getStringList("BountyList");
		
		if(!bountylist.contains(p.getName())) bountylist.add(p.getName());
		
		Main.getBounty().set("BountyList", bountylist);
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {			
			String sql1 = "UPDATE " + Main.DB_TABLE + " SET bounty=" + amount + " WHERE uuid = '" + uuid + "'";
			String sql2 = "UPDATE " + Main.DB_TABLE + " SET TotalBounty=" + total + " WHERE uuid = '" + uuid + "'"; 
			
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
			
			Main.bounty.set("Bounties." + uuid + ".Amount", amount);
			Main.bounty.set("Bounties." + uuid + ".TotalBounty", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will set the amount
	 * to the specified players
	 * bounty kill streak.
	 * 
	 * @param p - The online player.
	 * @param amount - The amount to set the bounty streak to. 
	 */
	public static void setBountyStreak(Player p, double amount) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		double total = amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET BountyStreak=" + total + " WHERE uuid = '" + uuid + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {			
			Main.bounty.set("Bounties." + uuid + ".BountyStreak", total);
			Main.saveBountyStatic();
		}
	}
	
	/**This will set the amount
	 * to the specified players
	 * bounty kill streak.
	 * 
	 * @param p - The offline player.
	 * @param amount - The amount to set the bounty streak to. 
	 */
	public static void setBountyStreak(OfflinePlayer p, double amount) {
		String uuid = p.getUniqueId().toString().replace("-", "");
		double total = amount;
		
		if(Main.getInstance().getConfig().getBoolean("Database.Enabled")) {
			
			String sql = "UPDATE " + Main.DB_TABLE + " SET BountyStreak=" + total + " WHERE uuid = '" + uuid + "'";
			
			try {
				Main.s.executeUpdate(sql);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else {			
			Main.bounty.set("Bounties." + uuid + ".BountyStreak", total);
			Main.saveBountyStatic();
		}
	}
}
