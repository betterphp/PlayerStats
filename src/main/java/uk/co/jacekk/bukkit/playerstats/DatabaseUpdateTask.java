package uk.co.jacekk.bukkit.playerstats;

import java.util.HashMap;
import java.util.Map.Entry;

import uk.co.jacekk.bukkit.baseplugin.v7.scheduler.BaseTask;
import uk.co.jacekk.bukkit.playerstats.data.PlayerData;

public class DatabaseUpdateTask extends BaseTask<PlayerStats> {
	
	public DatabaseUpdateTask(PlayerStats plugin){
		super(plugin);
	}
	
	public void run(){
		HashMap<String, PlayerData> reset = new HashMap<String, PlayerData>();
		
		HashMap<String, PlayerData> updatePlayers = new HashMap<String, PlayerData>();
		
		HashMap<String, PlayerData> updateBlocksPlaced = new HashMap<String, PlayerData>();
		HashMap<String, PlayerData> updateBlocksBroken = new HashMap<String, PlayerData>();
		
		HashMap<String, PlayerData> updateMobKills = new HashMap<String, PlayerData>();
		HashMap<String, PlayerData> updateMobDeaths = new HashMap<String, PlayerData>();
		HashMap<String, PlayerData> updatePlayerDeaths = new HashMap<String, PlayerData>();
		HashMap<String, PlayerData> updateSuicides = new HashMap<String, PlayerData>();
		
		HashMap<String, PlayerData> updatePlayerKills = new HashMap<String, PlayerData>();
		
		HashMap<String, PlayerData> updateSession = new HashMap<String, PlayerData>();
		
		for (Entry<String, PlayerData> entry : plugin.playerDataManager.getAll().entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			if(data.activeTime > 0 || data.distanceTravelled > 0){
				updateSession.put(playerName, data);
			}
			
			if (data.totalChatMessages > 0 || data.totalCommands > 0 || data.lastJoinTime == data.lastUpdate || data.activeTime > 0 || data.distanceTravelled > 0){
				updatePlayers.put(playerName, data);
			}
			
			if (data.blocksPlaced.size() > 0){
				updateBlocksPlaced.put(playerName, data);
				updateSession.put(playerName, data);
			}
			
			if (data.blocksBroken.size() > 0){
				updateBlocksBroken.put(playerName, data);
				updateSession.put(playerName, data);
			}
			
			if (data.mobsKilled.size() > 0){
				updateMobKills.put(playerName, data);
			}
			
			if (data.mobDeaths.size() > 0){
				updateMobDeaths.put(playerName, data);
				updateSession.put(playerName, data);
			}
			
			if (data.playersKilled.size() > 0){
				updatePlayerKills.put(playerName, data);
			}
			
			if(data.playerDeaths.size() > 0){
				updatePlayerDeaths.put(playerName, data);
				updateSession.put(playerName, data);
			}
			
			if(data.suicides.size() > 0){
				updateSuicides.put(playerName, data);
				updateSession.put(playerName, data);
			}
		}
		
		if (updatePlayers.size() > 0){
			plugin.log.info("Player data updated");
			plugin.mysql.performQuery(QueryBuilder.updatePlayers(updatePlayers));
		}
		
		if (updateBlocksPlaced.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updateBlocksPlaced(updateBlocksPlaced));
		}
		
		if (updateBlocksBroken.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updateBlocksBroken(updateBlocksBroken));
		}
		
		if (updateMobKills.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updateMobKills(updateMobKills));
		}
		
		if (updateMobDeaths.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updateMobDeaths(updateMobDeaths));
		}
		
		if (updatePlayerKills.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updatePlayerKills(updatePlayerKills));
		}
		
		if(updatePlayerKills.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updatePlayerDeaths(updatePlayerDeaths));
		}
		
		if(updateSuicides.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updatePlayerSuicides(updateSuicides));
		}
		
		if(updateSession.size() > 0){
			plugin.mysql.performQuery(QueryBuilder.updateSession(updateSession));
		}
		
		reset.putAll(updatePlayers);
		
		reset.putAll(updateBlocksPlaced);
		reset.putAll(updateBlocksBroken);
		
		reset.putAll(updateMobKills);
		reset.putAll(updateMobDeaths);
		reset.putAll(updatePlayerDeaths);
		reset.putAll(updateSuicides);
		
		reset.putAll(updatePlayerKills);
		
		for (Entry<String, PlayerData> entry : reset.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			if (plugin.server.getPlayer(playerName) == null){
				if(data.logoutTime == -1)
					data.logoutTime = System.currentTimeMillis() / 1000;
				plugin.mysql.performQuery(QueryBuilder.endSession(playerName, data));
				plugin.playerDataManager.unregisterPlayer(playerName);
			}else{
				data.reset();
			}
		}
	}
	
}