package uk.co.jacekk.bukkit.playerstats;

import java.io.File;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.playerstats.data.PlayerDataListener;
import uk.co.jacekk.bukkit.playerstats.data.PlayerDataManager;
import uk.co.jacekk.bukkit.playerstats.mysql.MySQLConnection;

public class PlayerStats extends BasePlugin {
	
	protected MySQLConnection mysql;
	
	public PlayerDataManager playerDataManager;
	
	private DatabaseUpdateTask databaseUpdateTask;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDir + File.separator + "config.yml"), Config.values(), this.log);
		
		this.mysql = new MySQLConnection(this.config.getString(Config.DATABASE_HOST), this.config.getString(Config.DATABASE_USER), this.config.getString(Config.DATABASE_PASS), this.config.getString(Config.DATABASE_DB_NAME));
		this.mysql.start();
		
		this.playerDataManager = new PlayerDataManager();
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		
	//	this.scheduler.scheduleSyncRepeatingTask(this, new TestTask(this), 40, 40); // 2 seconds
		this.databaseUpdateTask = new DatabaseUpdateTask(this);
		this.scheduler.scheduleSyncRepeatingTask(this, this.databaseUpdateTask, 600, 600); // 30 seconds
	}
	
	public void onDisable(){
		this.databaseUpdateTask.run();
		
		this.mysql.waitUntilDone();
		this.mysql.stopThread();
	}
	
}
