package uk.co.jacekk.bukkit.playerstats;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.playerstats.data.PlayerDataListener;
import uk.co.jacekk.bukkit.playerstats.data.PlayerDataManager;
import uk.co.jacekk.bukkit.playerstats.mysql.MySQLConnection;

public class PlayerStats extends BasePlugin {
	
	protected MySQLConnection mysql;
	
	public PlayerDataManager playerDataManager;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.mysql = new MySQLConnection("127.0.0.1", "root", "notmypassword", "minecraft_server");
		this.mysql.start();
		
		this.playerDataManager = new PlayerDataManager();
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new TestTask(this), 40, 40); // 2 seconds
		this.scheduler.scheduleSyncRepeatingTask(this, new DatabaseUpdateTask(this), 600, 600); // 30 seconds
	}
	
	public void onDisable(){
		
	}
	
}
