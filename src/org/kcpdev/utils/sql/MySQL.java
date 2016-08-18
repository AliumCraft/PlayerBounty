package org.kcpdev.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;
import org.kcpdev.utils.DatabaseBase;

public class MySQL extends DatabaseBase {

	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;
	
	protected MySQL(Plugin pl, String hostname, String port, String database, String username, String password) {
		super(pl);
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
	}

	@Override
	public Connection openConnection() 
			throws SQLException, ClassNotFoundException {
		if(checkConnection()) {
			return this.con;
		}
		
		Class.forName("com.mysql.jdbc.Driver");
		this.con = DriverManager.getConnection("jdbc:mysql//" +
				this.hostname + ":" + this.port + "/" + this.database,
				this.user, this.password);
		return this.con;
	}

}
