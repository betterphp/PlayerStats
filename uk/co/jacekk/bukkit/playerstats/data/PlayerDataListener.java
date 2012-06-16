package uk.co.jacekk.bukkit.playerstats.data;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import uk.co.jacekk.bukkit.baseplugin.BaseListener;
import uk.co.jacekk.bukkit.playerstats.PlayerStats;

public class PlayerDataListener extends BaseListener<PlayerStats> {
	
	public PlayerDataListener(PlayerStats plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerChat(PlayerChatEvent event){
		PlayerData data = plugin.playerDataManager.getDataFor(event.getPlayer().getName());
		++data.totalChatMessages;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		PlayerData data = plugin.playerDataManager.getDataFor(event.getPlayer().getName());
		++data.totalCommands;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		PlayerData data = plugin.playerDataManager.getDataFor(event.getPlayer().getName());
		data.addBlockBreak(event.getBlock().getType());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBucketFill(PlayerBucketFillEvent event){
		PlayerData data = plugin.playerDataManager.getDataFor(event.getPlayer().getName());
		data.addBlockBreak(event.getBlockClicked().getRelative(event.getBlockFace()).getType());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event){
		PlayerData data = plugin.playerDataManager.getDataFor(event.getPlayer().getName());
		data.addBlockPlace(event.getBlock().getType());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBucketEmpty(PlayerBucketEmptyEvent event){
		PlayerData data = plugin.playerDataManager.getDataFor(event.getPlayer().getName());
		
		Material bucketType = event.getPlayer().getItemInHand().getType();
		
		data.addBlockPlace((bucketType == Material.WATER_BUCKET) ? Material.WATER : Material.LAVA);
	}
	
}
