package uk.co.jacekk.bukkit.playerstats.data;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PlayerData {
	
	// the players this data is for
	private Player player;
	
	// the number of chat messages a player has sent
	public int totalChatMessages;
	public int totalCommands;
	
	// the number of blocks a player has changed
	public HashMap<Material, Integer> blocksBroken;
	public HashMap<Material, Integer> blocksPlaced;
	
	// how many times a player has killed something
	public HashMap<EntityType, Integer> mobsKilled;
	public HashMap<String, Integer> playersKilled;
	
	// how many times a player has been killed
	public HashMap<EntityType, Integer> mobDeaths;
	
	// the time the player last joined
	public long lastJoinTime;
	
	// the the the data was last pushed to the database.
	public long lastUpdate;
	
	// The direction that the player was looking when we last updated
	public Direction lastDirection;
	
	public PlayerData(Player player){
		this.player = player;
		
		this.reset();
		
		this.lastJoinTime = this.lastUpdate;
	}
	
	public void reset(){
		this.totalChatMessages = 0;
		this.totalCommands = 0;
		
		this.blocksBroken = new HashMap<Material, Integer>();
		this.blocksPlaced = new HashMap<Material, Integer>();
		
		this.mobsKilled = new HashMap<EntityType, Integer>();
		this.playersKilled = new HashMap<String, Integer>();
		
		this.mobDeaths = new HashMap<EntityType, Integer>();
		
		this.lastUpdate = System.currentTimeMillis() / 1000;
		
		this.lastDirection = new Direction(player.getLocation());
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public void addBlockBreak(Material type){
		this.blocksBroken.put(type, (this.blocksBroken.containsKey(type)) ? this.blocksBroken.get(type) + 1 : 1);
	}
	
	public void addBlockPlace(Material type){
		this.blocksPlaced.put(type, (this.blocksPlaced.containsKey(type)) ? this.blocksPlaced.get(type) + 1 : 1);
	}
	
	public void addMobKill(EntityType type){
		this.mobsKilled.put(type, (this.mobsKilled.containsKey(type)) ? this.mobsKilled.get(type) + 1 : 1);
	}
	
	public void addMobDeath(EntityType type){
		this.mobDeaths.put(type, (this.mobDeaths.containsKey(type)) ? this.mobDeaths.get(type) + 1 : 1);
	}
	
	public void addPlayerKill(String name){
		this.playersKilled.put(name, (this.playersKilled.containsKey(name)) ? this.playersKilled.get(name) + 1 : 1);
	}
	
}
