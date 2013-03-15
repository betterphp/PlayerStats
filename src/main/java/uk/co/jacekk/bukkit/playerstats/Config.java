package uk.co.jacekk.bukkit.playerstats;

import uk.co.jacekk.bukkit.baseplugin.v9_1.config.PluginConfigKey;

public class Config extends PluginConfigKey {
	
	public static final Config DATABASE_HOST = new Config(		"database.host",	"127.0.0.1");
	public static final Config DATABASE_USER = new Config(		"database.user",	"example_user");
	public static final Config DATABASE_PASS = new Config(		"database.pass",	"example_pass");
	public static final Config DATABASE_DB_NAME = new Config(	"database.db-name",	"minecraft_server");
	
	private Config(String key, Object defaultValue){
		super(key, defaultValue);
	}
}
