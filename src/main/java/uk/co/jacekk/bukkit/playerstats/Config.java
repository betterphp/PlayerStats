package uk.co.jacekk.bukkit.playerstats;

import uk.co.jacekk.bukkit.baseplugin.v1.config.PluginConfigKey;

public enum Config implements PluginConfigKey {
	
	DATABASE_HOST(		"database.host",	"127.0.0.1"),
	DATABASE_USER(		"database.user",	"example_user"),
	DATABASE_PASS(		"database.pass",	"example_pass"),
	DATABASE_DB_NAME(	"database.db-name",	"minecraft_server");
	
	private String key;
	private Object defaultValue;
	
	private Config(String key, Object defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public Object getDefault(){
		return this.defaultValue;
	}
	
}
