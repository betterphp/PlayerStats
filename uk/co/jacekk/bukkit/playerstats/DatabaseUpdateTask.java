package uk.co.jacekk.bukkit.playerstats;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import uk.co.jacekk.bukkit.baseplugin.BaseTask;
import uk.co.jacekk.bukkit.playerstats.data.PlayerData;

public class DatabaseUpdateTask extends BaseTask<PlayerStats> {
	
	public DatabaseUpdateTask(PlayerStats plugin){
		super(plugin);
	}
	
	public void run(){
		HashSet<PlayerData> reset = new HashSet<PlayerData>();
		
		HashMap<String, PlayerData> updatePlayers = new HashMap<String, PlayerData>();
		HashMap<String, PlayerData> updateBlocksPlaced = new HashMap<String, PlayerData>();
		HashMap<String, PlayerData> updateBlocksBroken = new HashMap<String, PlayerData>();
		
		for (Entry<String, PlayerData> entry : plugin.playerDataManager.getAll().entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			if (data.totalChatMessages > 0 || data.totalCommands > 0){
				updatePlayers.put(playerName, data);
			}
			
			if (data.blocksPlaced.size() > 0){
				updateBlocksPlaced.put(playerName, data);
			}
			
			if (data.blocksBroken.size() > 0){
				updateBlocksBroken.put(playerName, data);
			}
		}
		
		if (updatePlayers.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updatePlayers(updatePlayers));
		}
		
		if (updateBlocksPlaced.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updateBlocksPlaced(updateBlocksPlaced));
		}
		
		if (updateBlocksBroken.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updateBlocksPlaced(updateBlocksBroken));
		}
		
		reset.addAll(updatePlayers.values());
		reset.addAll(updateBlocksPlaced.values());
		reset.addAll(updateBlocksBroken.values());
		
		for (PlayerData data : reset){
			data.reset();
		}
	}
	
}