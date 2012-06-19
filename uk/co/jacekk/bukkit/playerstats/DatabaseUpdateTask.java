package uk.co.jacekk.bukkit.playerstats;

import java.util.ArrayList;
import java.util.Map.Entry;

import uk.co.jacekk.bukkit.baseplugin.BaseTask;
import uk.co.jacekk.bukkit.playerstats.data.PlayerData;

public class DatabaseUpdateTask extends BaseTask<PlayerStats> {
	
	public DatabaseUpdateTask(PlayerStats plugin){
		super(plugin);
	}
	
	public void run(){
		ArrayList<Entry<String, PlayerData>> updatePlayers = new ArrayList<Entry<String, PlayerData>>();
		
		for (Entry<String, PlayerData> entry : plugin.playerDataManager.getAll().entrySet()){
			if (entry.getValue().writeNeeded()){
				updatePlayers.add(entry);
			}
		}
		
		if (updatePlayers.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updatePlayers(updatePlayers));
		}
		
		for (Entry<String, PlayerData> entry : updatePlayers){
			entry.getValue().reset();
		}
	}
	
}
