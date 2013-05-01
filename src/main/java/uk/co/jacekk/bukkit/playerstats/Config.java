package uk.co.jacekk.bukkit.playerstats;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfigKey;

public class Config {
	
	public static final PluginConfigKey DATABASE_HOST = new PluginConfigKey("database.host", "127.0.0.1");
	public static final PluginConfigKey DATABASE_USER = new PluginConfigKey("database.user", "example_user");
	public static final PluginConfigKey DATABASE_PASS = new PluginConfigKey("database.pass", "example_pass");
	public static final PluginConfigKey DATABASE_DB_NAME = new PluginConfigKey("database.db-name", "minecraft_server");
	
}
