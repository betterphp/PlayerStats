package uk.co.jacekk.bukkit.playerstats.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

public class MySQLConnection extends Thread implements Runnable {
	
	private String url;
	private String user;
	private String pass;
	
	private Connection connection;
	
	private LinkedBlockingQueue<String> queryQueue;
	
	public MySQLConnection(String server, String user, String pass, String dbName){
		this.url = "jdbc:mysql://" + server + "/" + dbName;
		this.user = user;
		this.pass = pass;
		
		try{
			this.connection = DriverManager.getConnection(this.url, this.user, this.pass);
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		this.queryQueue = new LinkedBlockingQueue<String>();
	}
	
	public void run(){
		String sql = "";
		
		while (true){
			try{
				if (this.connection.isClosed()){
					this.connection = DriverManager.getConnection(this.url, this.user, this.pass);
				}
				
				sql = this.queryQueue.take();
				
				this.connection.createStatement().execute(sql);
			}catch (InterruptedException e){
				try{
					this.connection.close();
				}catch (SQLException e1){
					e1.printStackTrace();
				}
				
				return;
			}catch (SQLException e){
				System.err.println("SQL Error: " + e.getMessage());
				System.err.println("In Query: " + sql);
			}
		}
	}
	
	public void performQuery(String sql){
		this.queryQueue.add(sql);
	}
	
}
