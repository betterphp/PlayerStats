package uk.co.jacekk.bukkit.playerstats.data;

import java.util.HashMap;

public class PlayerDataManager {
	
	private HashMap<String, PlayerData> data;
	
	public PlayerDataManager(){
		this.data = new HashMap<String, PlayerData>();
	}
	
	public void registerPlayer(String playerName){
		this.data.put(playerName, new PlayerData());
	}
	
	public void unregisterPlayer(String playerName){
		this.data.remove(playerName);
	}
	
	public boolean gotDataFor(String playerName){
		return this.data.containsKey(playerName);
	}
	
	public PlayerData getDataFor(String playerName){
		return this.data.get(playerName);
	}
	
	public HashMap<String, PlayerData> getAll(){
		return this.data;
	}
	
}
