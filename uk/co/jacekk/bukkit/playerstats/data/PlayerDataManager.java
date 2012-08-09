package uk.co.jacekk.bukkit.playerstats.data;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerDataManager {
	
	private HashMap<String, PlayerData> data;
	
	public PlayerDataManager(){
		this.data = new HashMap<String, PlayerData>();
	}
	
	public void registerPlayer(Player player){
		this.data.put(player.getName(), new PlayerData(player));
	}
	
	public void unregisterPlayer(String playerName){
		this.data.remove(playerName);
	}
	
	public void unregisterPlayer(Player player){
		this.unregisterPlayer(player.getName());
	}
	
	public boolean gotDataFor(String playerName){
		return this.data.containsKey(playerName);
	}
	
	public boolean gotDataFor(Player player){
		return this.gotDataFor(player.getName());
	}
	
	public PlayerData getDataFor(String playerName){
		return this.data.get(playerName);
	}
	
	public PlayerData getDataFor(Player player){
		return this.getDataFor(player.getName());
	}
	
	public HashMap<String, PlayerData> getAll(){
		return this.data;
	}
	
}
