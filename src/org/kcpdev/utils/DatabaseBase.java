package org.kcpdev.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.Plugin;

public abstract class DatabaseBase {
	protected Connection con;
	protected Plugin pl;
	
	protected DatabaseBase(Plugin pl) {
		this.pl = pl;
		this.con = null;
	}
	
	public abstract Connection openConnection() 
			throws SQLException, ClassNotFoundException;
	
	
	public boolean checkConnection()
			throws SQLException {
		return (con != null) && (!this.con.isClosed());
	}
	
	
	public Connection getConnection() {
		return this.con;
	}
	
	
	public boolean closeConnection() 
			throws SQLException {
		if(this.con == null) return false;
		
		this.con.close();
		return true;
	}
	
	
	public ResultSet querySQL(String query) 
			throws SQLException, ClassNotFoundException {
		if(!checkConnection()) {
			openConnection();
		}
		
		Statement statement = this.con.createStatement();
		ResultSet result = statement.executeQuery(query);
		
		return result;
	}
	
	
	public int updateSQL(String query) 
			throws SQLException, ClassNotFoundException {
		if(!checkConnection()) {
			openConnection();
		}
		
		Statement statement = this.con.createStatement();
		int result = statement.executeUpdate(query);
		
		return result;
	}
}
