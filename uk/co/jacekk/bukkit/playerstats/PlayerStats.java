package uk.co.jacekk.bukkit.playerstats;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.playerstats.data.PlayerDataManager;

public class PlayerStats extends BasePlugin {
	
	public PlayerDataManager playerDataManager;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.playerDataManager = new PlayerDataManager();
	}
	
}
